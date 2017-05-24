# Crosswalk demo

## Description

Shows in the simple example how to use XWalkView in the android app.

## How to install Crosswalk library in your app

1. Add to your project build.gradle to the repositories

  ```
  maven {
  url 'https://download.01.org/crosswalk/releases/crosswalk/android/maven2'
  }
  ```

2. Add to your app build.gradle the last Crosswalk library to dependencies ([All versions](https://download.01.org/crosswalk/releases/crosswalk/android/maven2/org/xwalk/xwalk_core_library/))

  ```
  compile 'org.xwalk:xwalk_core_library:23.53.589.4'
  ```

3. Now you can use all classes of the crosswalk library ([javadoc](https://crosswalk-project.org/apis/embeddingapidocs_v7/index.html))
