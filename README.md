# interviewProject

file stracture:
```
|di
 - providers
|model
 -soundItem
 -soundItemUI
|network
 -Api
|util
 -FileUtil 
|view
 |adapters
  - SoundAdspter
 - SoundActivity
|viewmodels
  -soundViewModel
  -soundViewModelFactory
```

- using corotinus for managing data and network and live data to send data to the view

-  using async corotinus to download the files in parallel

- for barcode i used external library: com.journeyapps:zxing-android-embedded

- using service locator Provider to "inject" all the main classes

- didn't use flow, but you can see the second branch "**with-multiple-files-and-progress-bar**" using flow and showing progress of downloaded apps
