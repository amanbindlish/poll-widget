# Poll Widget - A widget for showing poll (Kotlin)

<div align="center">
  <a href="https://amanbindlish.com">
    <img alt="Logo" src="https://user-images.githubusercontent.com/50623363/82434256-2fd5d880-9ac5-11ea-9d2f-8d2db5d18340.png" width="100" />
  </a>
</div>

<h2 align="center">
  Poll Widget - Kotlin
</h2>

#### App Sample
<div align="center">
  <p>
    <img src="/media/poll_widget_text.gif"/>	&nbsp;	&nbsp;	&nbsp;<img src="/media/poll_widget_image.gif"/>
    <img src="/media/poll_widget_demo.jpg" width="300" height="550"/>
  </p>
</div>

I have created 2 types of poll widgets:
- A Poll Widget with Text
- A Poll Widget with Images

#### Specifications:
- I have not focused about the fancy design, images and the timer animation.
- Code has been tested with UI test.
- Image list scrollable with 4 elements.
- UI retains state between orientation change (without any manifest modification to android:configChanges).
- Percentages update over time (eg every 1sec).

#### Functionalities:
- The complete project is written in Kotlin.
- Both widgets are on a single screen (1st is Poll with text and 2nd is Poll with Image).
- UI and data classes are segregated from each other.
- The whole of the code is tested against UI test (6 test cases written in MainFragmentTest.kt)
- The screen(poll widgets) handles configuration changes also.
- Data is stored in a JSON file locally and data is being saved/updated using preferences.
- Widgets take care of all the vote counts and percentages. A user can click on Vote again button to vote again (hence the percentage will be updated).

#### App Architecture
Based on MVP architecture and repository pattern, where database serves as a single source of truth for data.

#### App Specs
- Minimum SDK 19
- [Java8](https://java.com/en/download/faq/java8.xml)
- [Kotlin](https://kotlinlang.org/)
- MVP Architecture
- [Mockito](https://site.mockito.org/) for implementing unit test cases.
- [Glide](https://github.com/bumptech/glide) for image loading.


You can reach out to me for any updates and explanation and can also create pull requests for your updates here.

Follow me on [<img src="https://img.icons8.com/nolan/64/twitter-squared.png" width="28"/>](https://twitter.com/bindlishaman) [<img src="https://img.icons8.com/nolan/64/linkedin.png" width="28"/>](https://www.linkedin.com/in/amanbindlish/)
