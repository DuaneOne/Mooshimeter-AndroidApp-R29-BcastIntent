# Mooshimeter-AndroidApp-R29-BcastIntent[b]Use Mooshimeter App to Perform Many Tasks[/b]

The latest beta version of the mooshimeter android app has the ability to send the meter reading values via broadcast intent messages.  These values can be processed by any other app on the phone which has intent receiver capability.   Here are some examples to show how this could be useful.
The intent receiver app used in these examples is Tasker by Crafty Apps EU,   $3 to buy (from the google play store) https://play.google.com/store/apps/details?id=net.dinglisch.android.taskerm     This app allows you to set up multiple profiles, each to perform one or more tasks (sets of actions such as send an email) based on certain contexts (usually events in our case such as receiving a value > 1.1 volts from the mooshimeter).   Tasker is very powerful and can be complex, but you can do some very useful things fairly easily.  If you use the same google account, you can install Tasker on all your android devices without additional costs.   There are other alternatives to Tasker, but it is very popular and well supported.  Root is not required for the examples below.

[b]Set up Tasker Preferences. [/b]  Press the top three small vertical squares in upper right corner of Tasker   >   Preferences   >  Under UI, remove Beginner mode   >   Under Misc, check Allow External Access.


[b]Example 1:   Display a popup message every time a broadcast intent is received from the mooshimeter. [/b]  

In the Tasker app:
Make sure PROFILES is selected    >   +   >   Event   >   System   >   Intent Received  >  Action:  com.mooshim.mooshimeter.CUSTOM_INTENT    >    press top left back arrow  >    New Task   +  >   New Task Name:  Popup1   >   +   >   Alert   >   Popup   >   Text:  Received  %val   >   press top left back arrow twice to get back to main Tasker page.
	
Taker can run in the background.  If the mooshimeter app if it is running and intent turned on, you should see the meter value every 10 seconds pop up.

Well, that shows it is working correctly, but not too useful.  Let’s try another example.

[b]Example 2A:  Tasker announces when the resistance is an open circuit [/b]  

In the Tasker app, set up a second Profile:
PROFILES selected   >   +   >  Event  > System   >    Intent Received    >   Action:  com.mooshim.mooshimeter.CUSTOM_INTENT    >   press top left back arrow   >    New Task   +  >   New Task Name:  Speak1   >  +   >   Alert   >   Say   >  Text:   open circuit    >  Engine: Voice: Google Text-to-speech Engine     >    Stream:   Notification   >    If   %val Equals 1E9   >   press top left back arrow twice to get back to main Tasker page.
	
Taker can run in the background.  If the mooshimeter app is running, set to resistance, intent turned on, and the probes are open, you should hear the announcements.  Make sure the phone notification volume is set correctly.

[b]Example 2B:  Tasker announces 1% resistance pass [/b]  
If you are doing quality control, you might want to know if the resistance (or voltage, current, etc because the value to Tasker is unitless) is within spec for several pieces.   So let’s change the example above to test a 4.7K  5% resistor.
Go back and edit the TASKS called Speak1  >  Text: Passed  >  If %val  Maths Greater Than 4465  >  +  > And %val  Maths Less Than 4935  >   press top left back arrow twice to get back to main Tasker page.

[b]Example 3:  Tasker send an email on high voltage [/b]

To send an email, we need to install another app which is a Tasker plugin, called MailTask by Marco Stornelli    https://play.google.com/store/apps/details?id=com.balda.mailtask       This app is free, but has limitations unless you buy.  However, you can run this example to see how it works for free.

We could set up a third Profile, but let’s just add a second Action to the Speak1 Task
Click on TASKS   >  Speak1 to edit  >  +  >   Plugin  >   MailTask   >   Configuration   >   To:   receivingEmailAddress   >  subject  se1  >  body  Tes1   >  check mark   >  If  %val  Maths Greater Than  1.1  >   press top left back arrow twice to get back to main Tasker page.

If the mooshimeter app is running, set to DCV, intent turned on, and you probe a AA battery, you should get an email with subject se1 and Tes1 in the body.   This can be very useful, but you could get in a situation of getting an email every 10s if you are not careful.   Try this:   Make sure PROFILES is selected  >   long press Speak1  until top menu changes  > press the three horizontal sliders  >  play with Cooldown Time or Limit Repeats until you get what you want.  >  press top left back arrow to get back to main Tasker page.
That is enough examples to get you started.  Top row, we used PROFILES and TASKS.    If you want to perform an action which includes the display, then you need to learn about SCENES.   And VARS gets more into programming (variables).


[b]Some details on how this works. [/b]  
The mooshimeter app sends a key:value pair every 10 seconds.  The key is “val” and the value is the displayed reading on the selected channel.

com.mooshim.mooshimeter.CUSTOM_INTENT  is the specific broadcast intent from the mooshimeter.   There may be many intents sent by other apps on the phone and this acts as a first level filter for the receiver.  

The broadcast intents are internal to a single phone; the intents are not broadcast outside of the phone.

The user must make sure the meter is set up to the correct unit, i.e, Tasker will not know if the value represents volts, amps, temperature, etc. 

The broadcast values been converted to base units.  A reading of 5.13mV is sent as 0.00513 or 5.13E-3.   8.245Kohm is sent as 8245.  

If the resistance measures an open, the app will broadcast a value of exactly 1E9 (1G).  

If speaking does not work, android settings  >  Language and Input  >  Text-to-speech options    pick one for your phone.

To send email, the phone must have a email setup.  This was tested with gmail.  A nice thing about MailTask plugin is you do not have to enter the sending email address or password.  It uses the android system primary email.

If you want your phone display always on, even on battery, then go to settings  >  display  >  Sleep never.    Note in Android 5, this feature has been removed, so install Stay Alive! by SyNetDev from the Play Store.
