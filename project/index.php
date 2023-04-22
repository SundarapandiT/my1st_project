<!DOCTYPE html>
<html>  
  <head>
    <title>TestYourSkill</title>
    <link rel="stylesheet" href="css1.css">
  </head>
  <body>
    <div class="header"> 
         <h1><b>T</b>est<b>Y</b>our<b>S</b>kill</h1>
         <a href="signup.php">Sign Up</a>
    </div>
      <div class="container">
      <h1>LOGIN</h1>
      <form method="POST" action="logincheck.php">
        <input type="text" id="username" placeholder="USERNAME" name="username" required><br><br>
        <input type="password" id="password"placeholder="PASSWORD" name="password" required><br><br>
        <button type="submit">SIGN IN</button><br>
        <p class="message">Not registered?<br> <a href="signup.php">Create an account</a></p>
      </form>
    </div>
  </body> 
</html>
