import cv2
import easyocr
import matplotlib.pyplot as plt
import matplotlib
import time

# Use TkAgg for interactive plotting
matplotlib.use('TkAgg')

# Initialize the EasyOCR reader
reader = easyocr.Reader(['en'])  # Specify the language, 'en' for English

# Function to display an image using matplotlib
def show_frame(frame):
    rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    plt.imshow(rgb_frame)
    plt.axis('off')  # Hide axis
    plt.draw()  # Update the figure
    plt.pause(0.001)  # Small pause to allow for real-time updates

# Function to detect the number plate using EasyOCR
def detect_number_plate(frame):
    # Convert the image to grayscale (optional for better accuracy)
    gray_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # Perform OCR detection on the grayscale frame
    result = reader.readtext(gray_frame)

    # Loop over the detected text
    for (bbox, text, prob) in result:
        # Draw a bounding box around the detected text
        top_left = tuple([int(val) for val in bbox[0]])
        bottom_right = tuple([int(val) for val in bbox[2]])
        cv2.rectangle(frame, top_left, bottom_right, (0, 255, 0), 2)

        # Display the detected text
        cv2.putText(frame, text, (top_left[0], top_left[1] - 10),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.9, (36, 255, 12), 2)

    return frame

def main():
    # Initialize your video capture (replace 0 with the video source if necessary)
    cap = cv2.VideoCapture(0)

    if not cap.isOpened():
        print("Error: Could not open video source.")
        return

    # Reduce frame size to lighten load on system
    cap.set(cv2.CAP_PROP_FRAME_WIDTH, 320)
    cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 240)

    plt.ion()  # Turn on interactive mode for real-time plotting
    fig = plt.figure()

    # Skip frame counter (process every nth frame)
    frame_skip = 5
    frame_count = 0

    try:
        while True:
            ret, frame = cap.read()
            if not ret:
                print("Error: Failed to capture frame.")
                break

            frame_count += 1
            if frame_count % frame_skip == 0:
                # Detect the number plate in the frame using EasyOCR
                frame_with_plate = detect_number_plate(frame)
                # Display the frame with number plate using matplotlib
                show_frame(frame_with_plate)
            else:
                # Just show the current frame without OCR for skipped frames
                show_frame(frame)

            # Add a simple break condition (e.g., press Ctrl+C to exit)
            time.sleep(0.1)  # Add delay for smoother updates

    except KeyboardInterrupt:
        print("Exiting...")

    finally:
        # Release the video capture
        cap.release()
        plt.ioff()  # Turn off interactive mode
        plt.close()

if __name__ == "__main__":
    main()
