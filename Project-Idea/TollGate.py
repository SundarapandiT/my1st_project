import cv2
import pytesseract
import numpy as np
import re
from geopy.distance import geodesic

def preprocess_image(image):
    # Convert to grayscale
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    
    # Apply Gaussian blur to reduce noise
    blurred_image = cv2.GaussianBlur(gray_image, (5, 5), 0)
    
    # Apply adaptive thresholding for better edge detection
    adaptive_thresh = cv2.adaptiveThreshold(blurred_image, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 2)
    
    # Apply Canny edge detection
    edges = cv2.Canny(adaptive_thresh, 100, 200)
    
    # Find contours and keep the largest one which likely contains the number plate
    contours, _ = cv2.findContours(edges, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    contours = sorted(contours, key=cv2.contourArea, reverse=True)[:10]
    
    number_plate_contour = None
    for contour in contours:
        # Approximate contour to polygon and check if it's rectangular
        approx = cv2.approxPolyDP(contour, 0.02 * cv2.arcLength(contour, True), True)
        if len(approx) == 4:
            number_plate_contour = approx
            break
    
    if number_plate_contour is not None:
        x, y, w, h = cv2.boundingRect(number_plate_contour)
        number_plate_image = image[y:y+h, x:x+w]
    else:
        number_plate_image = image  # Fallback to use the whole image if no contour is found
    
    return number_plate_image

def extract_number_plate(image):
    # Apply OCR to the preprocessed image to extract text
    number_plate_text = pytesseract.image_to_string(image, config='--psm 8')
    
    # Clean up the extracted text
    number_plate = re.sub(r'\W+', '', number_plate_text).upper()
    
    return number_plate

def map_registration_to_location(registration_number):
    # Detailed mapping of vehicle registration codes to geographic coordinates and additional info
    regional_map = {
        'TN67BC1234': {
            'coords': (11.4333, 79.7000),  # Example coordinates near Chidambaram, within 5 km radius
            'driver_name': 'John Doe',
            'registration_year': 2020,
            'address': 'Chidambaram, Tamil Nadu'
        },
        'TN69AK4321': {
            'coords': (11.3833, 79.6500),  # Example coordinates near Chidambaram, within 10 km radius
            'driver_name': 'Jane Smith',
            'registration_year': 2019,
            'address': 'Kollidam, Tamil Nadu'
        },
        'TN82Y8388': {
            'coords': (11.3500, 79.7500),  # Example coordinates near Chidambaram, within 15 km radius
            'driver_name': 'Alice Brown',
            'registration_year': 2021,
            'address': 'Cuddalore, Tamil Nadu'
        },
        'TN12DE1433': {
            'coords': (11.2900, 79.7800),  # Example coordinates near Chidambaram, within 20 km radius
            'driver_name': 'Bob Johnson',
            'registration_year': 2022,
            'address': 'Veppur, Tamil Nadu'
        },
        # Add more mappings as required
    }
    
    # Use the full registration number for exact lookup
    return regional_map.get(registration_number, None)

def calculate_distance(coord1, coord2):
    # Calculate the geodesic distance between two coordinates
    return geodesic(coord1, coord2).km

def main(image_path):
    try:
        image = cv2.imread(image_path)
        if image is None:
            raise FileNotFoundError(f"Image not found at {image_path}")

        # Preprocess the image and extract the number plate
        preprocessed_image = preprocess_image(image)
        number_plate = extract_number_plate(preprocessed_image)
        print(f"Extracted Number Plate: {number_plate}")

        # Map registration number to a geographic location and additional details
        vehicle_info = map_registration_to_location(number_plate)

        if vehicle_info:
            vehicle_coords = vehicle_info['coords']
            # Toll gate coordinates (Chidambaram)
            toll_gate_coords = (11.4064, 79.6935)
            distance = calculate_distance(toll_gate_coords, vehicle_coords)
            print(f"Calculated distance for {number_plate}: {distance} km")

            # Print additional vehicle information
            print(f"Driver Name: {vehicle_info['driver_name']}")
            print(f"Registration Year: {vehicle_info['registration_year']}")
            print(f"Address: {vehicle_info['address']}")

            if distance <= 20:
                print("You can go now!!!")
            else:
                print("Please Stop.")
        else:
            print(f"Registration number {number_plate} not found in the map.\n Please Stop!!!")

    except FileNotFoundError as e:
        print(str(e))
    except Exception as e:
        print(f"An error occurred: {str(e)}")

if __name__ == "__main__":
    image_path = 'image copy.png'  # Replace with your image path
    main(image_path)
