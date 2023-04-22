<?php
if(!isset($_POST['submit']))
{
    $name=$_POST['name'];
    $username=$_POST['username'];
    $email=$_POST['email'];
    $password=$_POST['password'];
    $mobile=$_POST['mobile'];
    $con=mysqli_connect("localhost:3306","root","","onlineexamination1");
    $sql2="SELECT * from explorer WHERE username='$username' AND password='$password'";
    $check=mysqli_query($con,$sql2);
    $checkcount=mysqli_num_rows($check);
    if($checkcount < 1)
    {
        $sql="INSERT into explorer(Name,Username,Email,Password,Mobile)VALUES('$name','$username','$email','$password','$mobile')";

        $result=mysqli_query($con,$sql);
        if($result)
        {
            echo '<script>alert("Signup Successfully")</script>';
            include('index.php');
        }
        else
        {
            echo '<script>alert("Something error SignUp again")</script>';
            include('signup.php');
        }
    }
    else
    {
        echo '<script>alert("Username and password already exist")</script>';
        include('signup.php');
    }  
} 
?>