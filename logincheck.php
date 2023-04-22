<?php
 if(!isset($_POST['submit']))
 {
    $username=$_POST['username'];
    $password=$_POST['password'];
    $con=mysqli_connect("localhost:3306","root","","onlineexamination1");
    $sql="SELECT * from explorer WHERE username='$username' AND password='$password'";
    $result=mysqli_query($con,$sql);
    $resultcheck=mysqli_num_rows($result);
    if($resultcheck > 0)
    {
        echo '<script>alert("Login Successfully")</script>';
    }
    else
    {
        echo '<script>alert("Username or Password Incorrect")</script>';
    }
 }