# Imagesearch_Cavista

## Requirements

#### 1. Error handling 
kotlin's (?) is mostly used to catch null exceptions. 
For network calls proper error handling is done

#### 2. Handling screen rotation
As this assignemnt required no special changes, on screen orientation activity refresh is handled by adding `android:configChanges="keyboardHidden|orientation|screenSize"`
to each activity in manifest file

#### 3. architecture patterns
MVC pattern is followed with 
1. Models - bussiness logic in helpers, utils and model classes
2. Views - XML files for UI
3. Controller - Activity and adapter classes to controll views and models.

#### 4. Clear code documentation
Every class is provided with java style documentation comments and also additional comments in between for better understanding of code. 

#### 5. Use of Git repository
Yes. 

---

## Bonus

#### 1. 250ms debounce in the search field
Using RxBindings debounce is added.
```
et_search.textChanges()
            .debounce(250, TimeUnit.MILLISECONDS)
```

#### 2. Design to accommodate phone and tablet
Seperate dimension files used for phone and tablets

#### 3. Any use of RxAndroid/RxJava/Android architecture components
 RxBindings used for debounce and text change detect on edittext for search
 
 In [ImageFetchHelper](https://github.com/Salimattivenkatvinay/Imagesearch_Cavista/blob/master/app/src/main/java/com/vinay/imagesearch/networkHelpers/ImageFetchHelper.kt)
 for `getImageList` `io.reactivex.rxjava3.core.Observable` is returned on subscribed in MainActivity for better bussines logic seperation

#### 4. Use of Constraint layouts
Almost all the layouts are made using Constraint layout. 

#### 5. Writing some tests
Sample unit test for [Convertor util class are added](https://github.com/Salimattivenkatvinay/Imagesearch_Cavista/blob/master/app/src/test/java/com/vinay/imagesearch/utils/ConverterUnitTest.kt) 

#### 6. Loading the next page/ Infinite scroll
Using `recyclerView!!.addOnScrollListener` loadmore is acheived.
Progressbar is displyed and hidden when next page is loaded.

