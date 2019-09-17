# RGBEverything
Control any set of RGB lights without the need of a central hub using your Android phone.

### Android App
Sign In | Lights Home | Light Details
--- | --- | ---
![alt text](https://github.com/GustavoSanMartin/RGBEverything/raw/master/images/sign_in.png "Sign In Screen") | ![alt text](https://github.com/GustavoSanMartin/RGBEverything/raw/master/images/lights.png "Lights Home Screen") | ![alt text](https://github.com/GustavoSanMartin/RGBEverything/raw/master/images/light_detail.png "Light Details Screen")

### Firebase
Architecture:</br>
lights:</br>
---light_id:</br>
------id,</br>
------name,</br>
------brightness,</br>
------color,</br>
------users:</br>
---------user_id</br>
                    
users:</br>
---user_id:</br>
------name,</br>
------associated_lights:</br>
---------light_id:</br>
------------name,</br>
------------id</br>
