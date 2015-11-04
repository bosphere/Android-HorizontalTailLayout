Android-HorizontalTailLayout
============================

A simple implementation of a custom layout which solves the issues of grouping a child view with its tailing views. 

One example is when right drawable used with `TextView`. To make sure text is wrapped properly, we normally want to have `TextView` extend to match the full width of its parent view, but the drawable would end up showing in the right-most position though having a pretty short length of text. 

What `HorizontalTailLayout` offers:
* smarter layout in general - guarantee that tailing views to follow closely when there is room for display, and squeeze only the first view otherwise
* a more flexible solution for `TextView` right drawable

<img src="./arts/demo.png" width="500">

Usage
-----

```
<com.bosphere.horizontaltaillayout.HorizontalTailLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:center="true">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ellipsize="end"
        android:singleLine="true"
        android:includeFontPadding="false"
        android:text="text (center)"/>

    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@android:drawable/ic_secure"/>

</com.bosphere.horizontaltaillayout.HorizontalTailLayout>
```

Compatibility
-------------

API 7 (Android 2.1) and up

License
-------

    Copyright 2014 Yang Bo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
