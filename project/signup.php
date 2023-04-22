<!DOCTYPE html>
<html>
  <head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="css_signup.css">
  </head>
  <body>
    <marquee behavior="" direction="right"><h2><b>T</b>est<b>Y</b>our<b>S</b>kill</h2></marquee>
    <div class="container">
      <form method="POST" action="getuser.php">
        <h1>Sign Up</h1>
        <!-- <label for="name">Name:</label> -->
        <input type="text" id="name" name="name" placeholder="NAME:" required>
        <!-- <label for="name">Username:</label> -->
        <input type="text" id="name" name="username" placeholder="USERNAME:" required>
        <!-- <label for="email">Email:</label> -->
        <input type="email" id="email" name="email" placeholder="EMAIL:" required>
        <!-- <label for="password">Password:</label> -->
        <input type="number" id="mobile" name="mobile" placeholder="MOBILE:" required>
        <input type="password" id="password" name="password" placeholder="PASSWORD" required>
        <input type="text" id="re_password" name="re_password" placeholder="RE_PASSWORD" required>
        <button type="submit">SIGN UP</button>
      </form>
    </div>
  </body>
</html>
