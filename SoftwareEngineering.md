---
layout: default
title: Software Design & Engineering
---
# Artifact's Origin

<p> Artifact : CS360 final project “Weight Tracking App” (Kotlin, XML, SQLite)
Developed on August 2026 for Mobile Architecture and Programming at SNHU
</p>
<div style="text-align: justify;">
<p>
The application was developed as part of a project that required Android Studio environment. It’s original goal was to design a friendly user interface that follows Android’s design foundations and best practices. The application’s requirements were to provide CRUD operations and a trusted database, either local or online, where user’s data such as weight, username and passwords can be stored.
</p>
</div>
---
# Justification
<div style="text-align: justify;">
<p>The original version of this submission implemented an unstructured SQLite helper that verifies the user’s authentications but violates single responsibility principle. The DashboardActivity module handles most of the operations for the application’s functionalities but hardcoded “test” username breaks the multi-user support, which does not follow best practices.
In this artifact, the CRUD operations must work properly without causing memory leaks, or accidentally exposing another user’s personal data. The modules currently don’t follow a structured architecture that can be used for improvements, so the enhancement includes refactoring the tree’s architecture in a professional manner, as well as adding accurate documentation with in-line comments to improve the application’s sustainability and readability. 
</p>
</div>
---
# Enhancement Steps
<h3 style="color:#0969da;">Fixing the data layer by updating DAO queries</h3>
<div style="text-align: justify;">
<p>The data layer needed to be corrected first. By creating data/local package and implementing WeightEntry as the entity, WeightDao for SQL queries , an AppDB for the database, I established a great foundation. After introducing WeightRepository and UserRepository as the single source of truth the UI of the application already improved as it was easier to maintain with a clear separation of my concerns for the data layer.</p>
<br>
</div>
<p align="center">
  <img width="620" height="324" alt="image" src="https://github.com/user-attachments/assets/c7dc833a-0823-454a-b1ff-bb8648e3d8db" />
  <br>
    <em>Figure 1: Updated DAO structure</em>
</p>
<br>
<p>WeightDao is now the only place that handles SQL, which enforce my separation concerns. Aditionally, the ViewModel no longer executes queries directly:</p>

```kotlin
@Dao
interface WeightDao {
    // Reads current user's weight history ordered newest to oldest
    @Query("SELECT * FROM weights WHERE username = :username ORDER BY id DESC")
    suspend fun getHistory(username: String): List<WeightEntry>

    // Deletes the weight selected by the current user using the weight's ID
    @Query("DELETE FROM weights WHERE id = :id")
    suspend fun deleteWeight(id: Int)

    // Creates (Inserts) new weight into the database
    @Insert
    suspend fun insert(entry: WeightEntry)

    // Gets latest goal weight from current user
    @Query("SELECT goalWeight FROM weights WHERE username = :username ORDER BY id DESC LIMIT 1")
    suspend fun getLastTarget(username: String): Double?
}
```
<p>No more harcoded <i>test</i> username, UserRepository has login and register and carry USERNAME via intent , so now each DAO query can filter by real username. Previously, the username was harcoded so the current’s user information was not carried between screens via Intent. This enforces user isolation and multi-user functionality, where the applation now sees the real session rather than the generic test user</p>

```kotlin
class DashboardActivity : AppCompatActivity() {

    private lateinit var username: String
    private var currentWeight = 180.0
    private var goalWeight = 220.0
    private lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_dashboard)

        // Gets username from login
        username = intent.getStringExtra("USERNAME")?: run {
            Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // Personalized greeting at the top to display current user
        findViewById<TextView>(R.id.tvWelcome).text = "Welcome, $username"

        val repository = WeightRepository(applicationContext)
```

<h3 style="color:#0969da;">Creating a source of truth by creating a WeightRepository class</h3>
<p>Do...</p>
<h3 style="color:#0969da;">Fixing the ViewModel layer</h3>
<p>Do...</p>
<h3 style="color:#0969da;">Updating the UI layer by refactoring the activities dashboard</h3>
<p>Do...</p>
---
# Challenges
<div style="text-align: justify;">
<p><b>I. Unknown references.</b> Some challenges included manifest errors when migrating all the activities modules to the ui folder. Android could not find the references at first because the mapping was modified. To mitigate this, I renamed the modules in manifest and fixed the imports in the Kotlin files.</p>
  <br>
<p><b>II. Exception errors.</b> I added a “Welcome, User” on every screen, however the build started failing with a NullPointerException at parseDebugLocalResources. After further debugging, found out that I had some illegal folders inside my mipmap. Deleting the entire folder and creating a new one via New > Image Asset created the correct mipmap-hdpi.</p>
  <br>
<p><b>III. Syntax errors.</b> Some errors were encountered such as forgetting pointers and semicolons in this Kotlin logic, such as incorrect layout_height values that should have been wrap_content instead, and missing attributes such as contentDescription for accessibility.</p>
  <br>
<p><b>IV. Nulls and crashes.</b> I had the harcoded “test” username in version 1.0 of this application to test against the database. Once I removed it, I forgot to pass the USERNAME extra on all my navigations, therefore, intent.getStringExtra(“USERNAME”) returned null. After tracing back to the intent, I added the username to every intent that opens another screen, with a null check with <i>finish()</i> at the beginning of onCreate. At the end, the history screen stopped crashing and I was able to see the correct loaded data by unique username.</p>
  <br>
<p><b>V. Adding more modules.</b> The first version was simple and effective, but not functional for a full-stack application. I was challenged to untangle old database calls and wiring new dependencies. I mitigated this by working one step at a time, structure migrating and verifying each screen loads correctly. This helped me strengthen my abilities to deliver proper separation, test and align with industry standards.</p>
</div>
---
# Outcomes
<div style="text-align: justify;">
<p>I am confident that the outcomes came out as expected. The enhanced version will now evaluate computing solution and managed trade-offs between architectural layers by demonstrating abilities to use skills and tools for the purpose of implementing a solution to accomplish future industry goals. It also implements industry tools such as Room, LiveData and repository pattern to build a maintainable architecture,which outline the outcome on delivering a professional and coherent application to our weight tracking audience. Finally, it fixes collaborative failures where a different user might see the first user’s personal data due to having <i>test</i> user hardcoded in the application, which mitigates design flaws and ensures privacy and enhanced security of personal data.</p>
  
<p>Overall, this enhancement helped the functionality and reliability of the application’s usage. It does not break the overall architecture and core foundation, it simply improves the areas that needed more coverage from the first release.</p>

</div>
<br>

<hr>
<p align="left">
  <a href="./index.html" style="display:inline-block; padding:8px 16px; background-color:#f6f8fa; color:#24292f; text-decoration:none; border-radius:6px; border:1px solid #d0d7de; font-weight:bold;">← Home</a>
</p>
