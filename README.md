# CircleProgressBar

![https://github.com/emre1512/FloatingActionMenu](https://img.shields.io/badge/platform-Android-green.svg?style=flat-square)
![https://github.com/emre1512/FloatingActionMenu](https://img.shields.io/badge/API-16+-orange.svg?style=flat-square)
![https://www.apache.org/licenses/LICENSE-2.0](https://img.shields.io/badge/licence-Apache%20v2.0-blue.svg?style=flat-square)
![https://github.com/emre1512/FloatingActionMenu](https://img.shields.io/badge/version-v1.0.0-ff69b4.svg?style=flat-square)

A simple library for creating circular progressbars for Android.

## Examples

![](https://media.giphy.com/media/l1J3Wd5kpydArA3M4/giphy.gif)

## Installation

- Get it via gradle: ``` compile 'com.emredavarci:floatingactionmenu:1.0.0' ```
## Usage

1) Add CircleProgressBar to your layout

<b>Warning:</b> This library allows only <b>4</b> menu actions. Not <b>3</b>, not <b>5</b>.  

```xml
      <com.emredavarci.floatingactionmenu.FloatingActionMenu
        android:id="@+id/floatingMenu"
        android:layout_width="match_parent"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"
        android:layout_height="80dp"                // Important! Height and radius values should be same!
        app:radius="80dp"                 // Important! Height and radius values should be same!
        app:shadowWidth="2dp" 
        app:scaleRatio="3"
        app:duration="200"
        app:bgColor="#4ad6bd"
        app:menuIconRotateAngle="225"
        app:menuIconWidth="30dp"
        app:menuIconHeight="30dp"
        app:menuIcon="@drawable/default_menu_icon"
        app:firstActionIcon="@drawable/fav_icon"
        app:secondActionIcon="@drawable/share_icon"
        app:thirdActionIcon="@drawable/search_icon"
        app:fourthActionIcon="@drawable/settings_icon">
    </com.emredavarci.floatingactionmenu.FloatingActionMenu>
```

2) Reach it from your activity/fragment etc.

```java
	FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.actionmenu);
        menu.setClickEvent(new FloatingActionMenu.ClickEvent() {
            @Override
            public void onClick(int index) {
                Log.d("TAG", String.valueOf(index)); // index of clicked menu item
            }
        });
```

You can modify it programmatically if you want

<b>Setters</b>

```java
menu.setRadius(radius); 		 // set radius of button
menu.setShadowWitdth(2); 			// set shadow width
menu.setScaleRatio(3); 		    // set scale ratio of button
menu.setDuration(300); 		    // set duration of opening animation
menu.setBgColor("#FF6FD99D"); 	    // set background color of button
menu.setMenuIconRotateAngle(225); 	  // set menu icon rotate angle
menu.setMenuIconWidth(30); 	   // set menu icon width
menu.setMenuIconHeight(30); 		 // set menu icon height
enu.setMenuIcon(R.drawable.menu_icon); 	// set menu icon
menu.setFirstActionIcon(R.drawable.first_icon);  // set first action icon
menu.setSecondActionIcon(R.drawable.second_icon);   // set second action icon
menu.setThirdActionIcon(R.drawable.third_icon);   // set third action icon
menu.setFourthActionIcon(R.drawable.fourth_icon);    // set fourth action icon
```

<b>Getters</b>

```java
menu.getRadius();     // get radius of button
menu.getShadowWitdth();      // get shadow width
menu.getScaleRatio();    // get scale ratio of button
menu.getDuration();    // get duration of opening animation
menu.getBgColor();   // get background color of button
menu.getMenuIconRotateAngle();   // get menu icon rotate angle
menu.getMenuIconWidth();  // get menu icon width
menu.getMenuIconHeight();     // get menu icon height
menu.getMenuIcon();       // get menu icon
menu.getFirstActionIcon();  // get first action icon
menu.getSecondActionIcon(); // get second action icon
menu.getThirdActionIcon(); // get third action icon
menu.getFourthActionIcon();  // get fourth action icon
```

## LICENSE

Copyright 2017 M. Emre Davarci

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.






