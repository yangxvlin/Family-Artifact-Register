# Family-Artifact-Register
[![Build Status](https://travis-ci.com/yangxvlin/Family-Artifact-Register.svg?token=p9qqDGGpt9pGxmkHR8yq&branch=master)](https://travis-ci.com/yangxvlin/Family-Artifact-Register) ![GitHub repo size](https://img.shields.io/github/repo-size/yangxvlin/Family-Artifact-Register) ![GitHub](https://img.shields.io/github/license/yangxvlin/Family-Artifact-Register) ![](https://img.shields.io/badge/plaform-android-blue)

## Forget Me Not
This in an app targets for: 
   - Handing down family artifacts over the years and in different cultures and countries.
   - Solving the need of sharing family artifacts.
   - Helping users pass down their invaluable artifacts over generations.

## Contributors
<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/chen-dudu"><img src="https://avatars1.githubusercontent.com/chen-dudu" width="100px;" alt="LiGuo Chen"/><br /><sub><b>LiGuo Chen</b></sub></a><br /><a href="https://github.com/yangxvlin/Family-Artifact-Register/commits?author=chen-dudu" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/HaichaoS"><img src="https://avatars2.githubusercontent.com/HaichaoS" width="100px;" alt="HaiChao Song"/><br /><sub><b>HaiChao Song</b></sub></a><br /><a href="https://github.com/yangxvlin/Family-Artifact-Register/commits?author=HaiChaoS" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/Dovermore"><img src="https://avatars2.githubusercontent.com/Dovermore" width="100px;" alt="ZhuoQun Huang"/><br /><sub><b>ZhuoQun Huang</b></sub></a><br /><a href="https://github.com/yangxvlin/Family-Artifact-Register/commits?author=Dovermore" title="Code">💻</a></td>
    <td align="center"><a href="https://yangxvlin.github.io"><img src="https://avatars2.githubusercontent.com/u/26871369?v=4" width="100px;" alt="XuLinYang"/><br /><sub><b>XuLinYang</b></sub></a><br /><a href="https://github.com/yangxvlin/Family-Artifact-Register/commits?author=yangxvlin" title="Code">💻</a></td>
  </tr>
</table>

<!-- ALL-CONTRIBUTORS-LIST:END -->

## Supervisor
<table>
  <tr>
   <td align="center"><a href="https://github.com/Ruizhil1"><img src="https://avatars1.githubusercontent.com/u/56992380?s=400&v=4" width="100px;" alt="RuiZhi Li (Richard)"/><br /><sub><b>RuiZhi Li (Richard)</b></sub></a><br /><a href="https://github.com/yangxvlin/Family-Artifact-Register/commits?author=Ruizhil1" title="Code">💻</a></td>
  </tr>
</table>

## Repository Structure
```
| /app 
      - application code
  /docs 
      - documentations
  .all-contributorsrc 
      - all contributers infomation
  .travis.yml 
      - continuous integration
  build.gradle 
      - android building script

```

## Technologies & Tools:
- ![](./docs/arch_design/Firebase.jpg)
```
dependencies {
    // material ui
    api 'com.google.android.material:material:1.1.0-alpha10'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'com.android.volley:volley:1.1.1'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.41"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'

    // Google map api
    implementation 'com.google.android.libraries.places:places:2.0.0'

    // test

    implementation 'com.google.firebase:firebase-storage:16.0.4'
    implementation 'com.google.firebase:firebase-database:16.0.4'
    testImplementation 'junit:junit:4.12'
    // Core library
    androidTestImplementation 'androidx.test:core:1.2.0'
    // AndroidJUnitRunner and JUnit Rules
    androidTestImplementation 'androidx.test:runner:1.2.0'
    androidTestImplementation 'androidx.test:rules:1.2.0'
    // Assertions
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    // Espresso dependencies
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

    // fire base
    implementation 'com.google.firebase:firebase-core:17.2.0'
    implementation 'com.google.firebase:firebase-auth:19.0.0'
    implementation 'com.google.firebase:firebase-firestore:21.0.0'

    // FirebaseUI for Firebase Realtime Database
    implementation 'com.firebaseui:firebase-ui-database:5.1.0'
    // FirebaseUI for Cloud Firestore
    implementation 'com.firebaseui:firebase-ui-firestore:5.1.0'
    // FirebaseUI for Firebase Auth
    implementation 'com.firebaseui:firebase-ui-auth:5.1.0'
    // FirebaseUI for Cloud Storage
    implementation 'com.firebaseui:firebase-ui-storage:5.1.0'

    // Required only if Facebook login support is required
    // Find the latest Facebook SDK releases here: https://goo.gl/Ce5L94
    implementation 'com.facebook.android:facebook-android-sdk:[5,6)'

    // Required only if Twitter login support is required
    // Find the latest Twitter SDK releases here: https://goo.gl/E5wZvQ
    implementation 'com.twitter.sdk.android:twitter-core:3.1.1'
    // Include all the Twitter APIs
    implementation 'com.twitter.sdk.android:twitter:3.1.1'
    // (Optional) Monetize using mopub
    implementation 'com.twitter.sdk.android:twitter-mopub:3.1.1'

    // butter knife
    // https://github.com/JakeWharton/butterknife
    implementation 'com.jakewharton:butterknife:10.1.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:10.1.0'


    // photo & video take
    // https://github.com/jkwiecien/EasyImage
    implementation 'com.github.jkwiecien:EasyImage:3.0.3'
    // photo & video compress, only image part used
    // https://github.com/Tourenathan-G5organisation/SiliCompressor
    implementation 'com.iceteck.silicompressorr:silicompressor:2.2.2'

    //Third party
    // circle image view
    implementation 'de.hdodenhof:circleimageview:3.0.1'
    // crop image library
    // https://github.com/ArthurHub/Android-Image-Cropper
    api 'com.theartofdev.edmodo:android-image-cropper:2.8.+'

    // timeline
    implementation 'com.github.vipulasri:timelineview:1.1.0'

    // beautiful toast message library
    // https://github.com/GrenderG/Toasty
    implementation 'com.github.GrenderG:Toasty:1.4.2'

    // ViewModel and LiveData (lifecycle component)
    implementation 'androidx.lifecycle:lifecycle-extensions:2.1.0'
    implementation 'androidx.lifecycle:lifecycle-common-java8:2.1.0'

    // room persistence library
    implementation 'androidx.room:room-runtime:2.2.0-rc01'
    annotationProcessor 'androidx.room:room-compiler:2.2.0-rc01'
    androidTestImplementation 'androidx.room:room-testing:2.2.0-rc01'

    // crop thumbnail from video
    // https://github.com/bumptech/glide
    implementation 'com.github.bumptech.glide:glide:4.10.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'

    // image slide view library
    // https://github.com/alibaba/UltraViewPager
    implementation('com.alibaba.android:ultraviewpager:1.0.7.7@aar') {
        transitive = true
    }
    // open dialog to page view images
    // https://github.com/smhdk/KM-Popup-Image-Slider?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=7336
    implementation 'com.github.smhdk:KM-Popup-Image-Slider:v1.2.1'

    // tab layout library
    // https://github.com/ogaclejapan/SmartTabLayout
    implementation 'com.ogaclejapan.smarttablayout:library:2.0.0@aar'
    //Optional: see how to use the utility.
    implementation 'com.ogaclejapan.smarttablayout:utils-v4:2.0.0@aar'

    // sticky recycler view library
    // https://github.com/TellH/RecyclerStickyHeaderView
    implementation 'com.github.TellH:RecyclerStickyHeaderView:1.1.0'

    // FlycoDialog library
    // https://github.com/H07000223/FlycoDialog_Master
    implementation 'com.flyco.dialog:FlycoDialog_Lib:1.3.2@aar'

    // Interactive Info Window Android library
    // https://github.com/Appolica/InteractiveInfoWindowAndroid
    implementation 'com.appolica:interactive-info-window:v1.0.6'

}
```

## Screen shoots
<table border="0">
     <tr>
        <td><img src="./docs/sign_in_cn.gif"></td>
        <td><img src="./docs/sign_up_cn.gif"></td>
        <td><img src="./docs/sign_out_cn.gif"></td>
     </tr>
</table>

## Requirements
|  Feature   | Link  |
|  ----  | ----  |
| User Authentication  | [pdf](./docs/requirements/UserAuthentication.pdf) |
| Upload Artifacts  | [pdf](./docs/requirements/UploadArtifacts.pdf) |
| Add new friend  | [pdf](./docs/requirements/Addnewfriend.pdf) |
| Artifacts Timeline  | [pdf](./docs/requirements/ArtifactsTimeline.pdf) |
| Artifact Commenting  | [pdf](./docs/requirements/ArtifactCommenting.pdf) |
| View Artifacts  | [pdf](./docs/requirements/ViewArtifacts.pdf) |
| Artifact Visibility  | [pdf](./docs/requirements/ArtifactVisibility.pdf) |
| Event Recommendation  | [pdf](./docs/requirements/EventRecommendation.pdf) |
| Artifact Maps  | [pdf](./docs/requirements/ArtifactMaps.pdf) |

## Architecture Design
<table>
    <tr>
        <td>Title</td>
        <td>Diagram</td>
        <td>Description</td>
     </tr>
     <tr>
        <td>HIgh-level Stack</td>
        <td><img src="./docs/arch_design/Firebase.jpg"></td>
        <td></td>
     </tr>

</table>
