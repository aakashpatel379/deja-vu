# Deja Vu

Deja Vu is a multiplayer memory card game.The main idea of this game revolves around remembering the position of the deck of cards. Initially the deck of cards is scattered on the screen. Each player then gets two chances to pick two different cards from different location. The cards have two state i.e., shown or closed. The cards are matched on same number and same color (either black or red). If the cards are not match, then they get back to closed state and each player must remember the positon
and value of the card. The aim of the players is to find cards with same value and color. When all the cards are matched the game ends and the player who guessed maximum number of correct pairs wins.

## Group members

| Name                   | Banner ID  | Email               |
| ---------------------- | ---------- | ------------------- |
| Aakash Patel           | B00807065  | ak553155@dal.ca     |
| Sampark Pradhan        | B00821733  | sm459977@dal.ca     |

## Description

The purpose of the software is to challenge player's ability to memorize cards and to thus improve it substantially. 
Compared to other applications in the market, it is quite easy to use and can turn out to be a fun activity to its user.
The backgroud of this software emanates from the ideology of thinking and building something simple which, one may want to use in leisure time, which solves or helps in particular issue and is at the same time fun activity to do. 
The memory component as its source of motivation is particularly helpful/playful to almost any age group from 5 year old kindergartner to 55 year old veteren. Thus being able to handle such a large potential user base it is something people wouldn't want themselves to be missing at. Compared to other similar apps available in the market, its sleek, simplistic and provides rich gaming experience to its user which barely one may argue at. 
For a potential user, thinking to install the application its a win-win situation since the app not only promises to improve his/her memory power but also make it whole as a wonderful experience.

### Users

Application's target user base ranges from kids to old age people who wants to improve on their memory skills. User does not need any specific skillset to experience better gameplay.

### Features

A bullet-point list of the software's key features (from a user's perspective).
- Play Game in guest mode.
- Two Player gaming with friend.
- Set your own avatar
- Change avatar
- Play game with/without music.
- Load saved game
- Zoomable gameplay 
- Shake to restart
- Get help

## Libraries

Libraries used as part of our project include

**google-gson:** Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Source [here](https://github.com/google/gson)

**firebase-auth**: Firebase Auth is a java library to let our users authenticate using Email and Password (With email as unique identifier). We have used this library to authenticate our normal users with email and password. Source (https://firebase.google.com/docs/auth/android/password-auth)

**firebase-database**: Firebase Database to a java library to maintain data related to the application on Firebase. We have used this library to maintain realtime data of users already authenticated. Source (https://firebase.google.com/docs/database/android/start)  

**google-play-services**: Play Services is a java based library to implement google based sign in into your appplication. We have used it to include Google based sign in for users who don't prefer normal mode of authentication. Source (https://developers.google.com/android/guides/setup)

**androidx-recyclerview**: A library to support recyclerview in AndroidX (since most of the newer libaries only support androidx). We have used it in  Avatar Selector feature. Source (https://developer.android.com/jetpack/androidx/migrate)

**material**: Androidx Material libary to support designing UI elements. Source (https://github.com/material-components/material-components-android/releases)

**androidx.annotation**: AndroidX library to annotate elements in application methods.  Source (https://developer.android.com/jetpack/androidx/releases/annotation) 


## Requirements

None

## Installation Notes

- To test Google based Sign in Authentication on Emulator with ease, make sure emulator device is added with google account early hand otherwise, the feature would take you through all the procedure (which might sometime cause emulator issues ). However feature works completely fine when ran on external device. 
- Basic installation steps to run the app.

## Final Project Status

We have implemented all 'minimum functionalities' and 'expected functionalities' presented in our updated project contract. In 'bonus functionalities' we have implemented save and restore game state and sound effects.

The milestones which were not accomplished belongs to bonus functionalities. They are :- Custom avtar, Save friends for quick recall, show friends online states. They were not accomplished as initial time was given to google play services and developing the game in unity which affected our schedule. Because of time constraints we cound not achieve these bonus functionalities.

If we were to continue we would like to complete the two player game play in different devices and save friend list of a user. We would also like in to bring new modifications to our game logic like shuffle cards for shake. We can change the animations of card and game to make it look more attractive.


### Minimum Functionality
- Feature 1 Game is playable for single player and random CPU opponent (Completed)
- Feature 2 Restarting Game for Shake (Completed) 

### Expected Functionality
- Feature 1 Two-player mode on single device (Completed)
- Feature 2 Select avatar from set of supplied images. (Completed)
- Feature 3 Magnification of game surface using two-finger multi-touch "pinch-to-zoom" (Completed)
- Feature 4 Animations to fold and unfold the playing cards. (Completed)
- Feature 5 Smarter CPU opponent. (Has semi-reliable "memory" of N previous turns.) (Completed)

### Bonus Functionality
- Feature 1 Save and restore games. (Completed)
- Feature 2 Custom Avatar (For future version)
- Feature 3 Save friends for quick recall.(For future version)
- Feature 4 Sound Effects.(Completed)
- Feature 5 Show friend's online states.(For future version)

## Code Examples

```
**Problem 1: We were facing issues with Grid view in Game Logic design. It was due to the complex logic to be handled in between the turns. We even tried with performing item click (with use of performItemClick method), but struggled to manage the item view. So finally we decided to move ahead with the recycler view since we already had implementation experience for that.

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        Resources resources = mContext.getResources();
        final int resourceId = resources.getIdentifier("item"+imageItemList.get(position), "drawable",
                mContext.getPackageName());
       myViewHolder.cardViewItem.setBackground(mContext.getResources().getDrawable(resourceId));

        myViewHolder.radioBtnItem.setChecked(position==lastCheckedPosition);
        myViewHolder.radioBtnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == lastCheckedPosition)
                {
                    myViewHolder.radioBtnItem.setChecked(false);
                    lastCheckedPosition=-1;
                }
                else
                {
                    lastCheckedPosition=position;
                    notifyDataSetChanged();
                }
            }
        });

    }    
```
```
**Problem 2: We need to implement the maginifcation inside the game and stil choose crads**
// The problem was in magnifaction we need to touch and open the cards.
// The code snipet below helped in solving our problem
   private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 3.0f));
            maxWidth = width - (width * mScaleFactor);
            maxHeight = height - (height * mScaleFactor);
            invalidate();
            return true;
        }
    }
```

## Functional Decomposition

We have followed structral way to keep our files in place. Our fundamental purpose for this was to keep our classes segregated. Our folder structure is as follows:-
-adapter: consists of CardImageAdapter,RecylerViewAdapter
-cpu: consists of CardRange,CardViewHolder, Cpu, PlayerType concrete classes and OnItemClickListener Interface.
-display: consists of AvatarActivity,CpuGameActivity,HomeActivity,LoginActivity,PlayerActivity,RegisterActivity,SplashActivity concerete classes and Callback Interface
-gameplay: consists of card,cardColor,CardDB, Game,GameData, UserGameData concrete classes and GameView, PlayerMoveCallback interface.
-model: consists of Guest,Player and User concrete classes.
-service: SoundService concrete class
-util: consists of PlayerGson,RunTimeTypeAdapterFactor,SharedPrefHelper concerete classes.

## High-level Organization

**Site map:-**

![Flowers](/images/SiteMap_dejaVu.png)


## Layout

### Wireframes:-

**Wireframe 1**  

![](/images/wireframe1.png)

**Wireframe 2**  

![](/images/wireframe2.png)

**Wireframe 3**  

![](/images/wireframe3.png)


### Prototypes

None

### Implementation

**Screenshots of all the screens**

**4. App Icon**

<img src="/images/Screen0.jpeg" alt="drawing" width="200"/>

**5. Splash Screen**

<img src="/images/Screen1.png" alt="drawing" width="200"/>

**6. Home Screen**

<img src="/images/Screen2.png" alt="drawing" width="200"/>

**7. Sound On**

<img src="/images/Screen3.png" alt="drawing" width="200"/>

**8. Sound Off**

<img src="/images/Screen4.png" alt="drawing" width="200"/>

**9. Quit Game**

<img src="/images/Screen5.png" alt="drawing" width="200"/>

**10. Login/Sign In Screen**

<img src="/images/Screen6.png" alt="drawing" width="200"/>

**11. Google User Sign In**

<img src="/images/Screen7.png" alt="drawing" width="200"/>

**13. Registration Screen**

<img src="/images/Screen8.png" alt="drawing" width="200"/>

**14. Game dashboard**  

<img src="/images/Screen10.png" alt="drawing" width="200"/>

**15. Help Screen** 

<img src="/images/Screen11.png" alt="drawing" width="200"/>

**16. Play game screen 1** - 

<img src="/images/Screen12.png" alt="drawing" width="425"/>

**17. Play game screen 2** 

<img src="/images/Screen13.png" alt="drawing" width="425"/>

**18. Magnification of the game screen**

<img src="/images/Screen14.png" alt="drawing" width="425"/>

**19. Exit Game (Save Dialog)**

<img src="/images/Screen15.png" alt="drawing" width="425"/>

**20. Restart Game (On Shake)** 

<img src="/images/Screen16.png" alt="drawing" width="425"/>

**21. Score screen**

<img src="/images/Screen17.png" alt="drawing" width="425"/>

**22. Winning dialog** 

<img src="/images/Screen18.png" alt="drawing" width="425"/>


## Future Work

Our future works include to add more feature like adding friends and playing with friends on different devices. We want to customize our avatar list by giving the user to have custom made avatars for their profile. We also want to save friends for quick recall. Custom animations for card faceup and card distribution can be achieved. 


## Sources

All Refereneces are given below: -
 
[1] "Sneaky Snitch (Kevin MacLeod) - Gaming Background Music (HD)", YouTube, 2019. [Online]. Available: https://www.youtube.com/watch?v=Cm0qaXi9THA&list=PLya__OBTLMkONuQDu0kHDCrml2xuEINSi&index=2. [Accessed: 24- Jul- 2019].

[2] "Beautiful Android Login and Signup Screens with Material Design | Sourcey", Sourcey.com, 2019. [Online]. Available: https://sourcey.com/articles/beautiful-android-login-and-signup-screens-with-material-design. [Accessed: 24- Jul- 2019].

[3] "sourcey/materiallogindemo", GitHub, 2019. [Online]. Available: https://github.com/sourcey/materiallogindemo/blob/master/app/src/main/res/values/styles.xml. [Accessed: 24- Jul- 2019].

[4]"Flaticon, the largest database of free vector icons", Flaticon, 2019. [Online]. Available: https://www.flaticon.com/. [Accessed: 24- Jul- 2019].

[5] E. Dunham, "Most Popular Fonts » Font Squirrel", Fontsquirrel.com, 2019. [Online]. Available: https://www.fontsquirrel.com/fonts/list/popular. [Accessed: 24- Jul- 2019].

[6] "firebase/quickstart-android", GitHub, 2019. [Online]. Available: https://github.com/firebase/quickstart-android/blob/375c1ae5ec9000ee71b93cee409086e27d774bdb/auth/app/src/main/java/com/google/firebase/quickstart/auth/java/GoogleSignInActivity.java#L186. [Accessed: 24- Jul- 2019].

[7] H. Studio, A. Das, A. Das, V. Makode, S. Sankla and M. Dahee, "How to use custom font in a project written in Android Studio", Stack Overflow, 2019. [Online]. Available: https://stackoverflow.com/questions/27588965/how-to-use-custom-font-in-a-project-written-in-android-studio. [Accessed: 24- Jul- 2019].

[8] S. RecyclerView, s. boyapati and I. Iqbal, "Single selection in RecyclerView", Stack Overflow, 2019. [Online]. Available: https://stackoverflow.com/questions/28972049/single-selection-in-recyclerview. [Accessed: 24- Jul- 2019].

[9] Play.google.com, 2019. [Online]. Available: https://play.google.com/store/apps/details?id=com.avabyte.memorymatch&hl=en. [Accessed: 24- Jul- 2019].

[10]"App Icon Generator", Appicon.co, 2019. [Online]. Available: https://appicon.co. [Accessed: 24- Jul- 2019].