package com.example.flags

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flags.dataclasses.Image
import com.example.flags.dataclasses.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.very_first_page)



    }
}

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
    }
}

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val image = intent.getParcelableExtra<Image>(Testing.INTENT_PARCELABLE)

        val imgSrc = findViewById<ImageView>(R.id._imageDetail)
        val imgTitle = findViewById<TextView>(R.id._imageTitle)
        val imgDesc = findViewById<TextView>(R.id._imageDesc)

        imgSrc.setImageResource(image!!.imageSrc)
        imgTitle.text = image!!.imageTitle
        imgDesc.text = image.imageDesc
    }
}

class Help : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)



    }
}

class ImageAdapter(
    private val context: Context,
    private val images: List<Image>,
    private val listener: (Image) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageSrc = view.findViewById<ImageView>(R.id._image)
        private val title = view.findViewById<TextView>(R.id._title)
        fun bindView(image: Image, listener: (Image) -> Unit) {
            imageSrc.setImageResource(image.imageSrc)
            title.text = image.imageTitle
            itemView.setOnClickListener { listener(image) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image, parent, false))

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(images[position], listener)
    }
}

class Main_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)
    }
}

class Menu : AppCompatActivity() {


    private lateinit var buttonCountry : Button
    private lateinit var buttonHelp: Button
    private lateinit var buttonProfile: Button
    private lateinit var buttonAboutUs : Button



    private lateinit var buttonLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        init()
        registerListeners()
        profileListeners()
        goToCountry()
        goToAboutUs()
        goToHelp()

    }

    private fun init() {
        buttonLogout = findViewById(R.id.button12)
        buttonCountry = findViewById(R.id.button7)
        buttonHelp = findViewById(R.id.button8)
        buttonProfile = findViewById(R.id.button9)
        buttonAboutUs = findViewById(R.id.button11)
    }


    private fun registerListeners() {
        buttonLogout.setOnClickListener() {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun profileListeners() {
        buttonProfile.setOnClickListener() {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }

    private fun goToCountry() {
        buttonCountry.setOnClickListener() {
            val intent = Intent(this, Testing::class.java)
            startActivity(intent)
        }
    }

    private fun goToAboutUs() {
        buttonAboutUs.setOnClickListener() {
            val intent = Intent(this, AboutUs::class.java)
            startActivity(intent)
        }
    }

    private fun goToHelp() {
        buttonHelp.setOnClickListener() {
            val intent = Intent(this, Help::class.java)
            startActivity(intent)
        }
    }


}

class Profile : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val dbUserInfo: DatabaseReference = FirebaseDatabase.getInstance().getReference("UserInfo")
    private val dbBooks: DatabaseReference = FirebaseDatabase.getInstance().getReference("Book")

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var editTextPersonName: TextView
    private lateinit var editTextUrl: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()

        registerListeners()

        dbUserInfo.child(auth.currentUser?.uid!!).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val userInfo = snapshot.getValue(UserInfo::class.java) ?: return

                textView.text = userInfo.name

                Glide.with(this@Profile)
                    .load(userInfo.imageUrl)
                    .into(imageView)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun init() {
        textView = findViewById(R.id.textView5)
        editTextPersonName = findViewById(R.id.editTextPersonName)
        editTextUrl = findViewById(R.id.editTextUrl)
        button = findViewById(R.id.button13)
    }


    private fun registerListeners() {

        button.setOnClickListener {

            val name = editTextPersonName.text.toString()
            val url = editTextUrl.text.toString()

            val userInfo = UserInfo(name, url)


            dbUserInfo.child(auth.currentUser?.uid!!).setValue(userInfo)

        }

    }





}

class Testing : AppCompatActivity() {

    private lateinit var button: Button


    companion object {
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testing)
        button = findViewById(R.id.button13)

        goToMenu()

        val imageList = listOf<Image>(
            Image(
                R.drawable.georgia,
                "საქართველო",
                "მოსახლეობა 3,709 მილიონი\n" +
                        " დედაქალაქი - თბილისი\n" +
                        " სახელმწიფო მდებარეობს ევრაზიაში, კავკასიაში, შავი ზღვის აღმოსავლეთ სანაპიროზე.\n" +
                        " დამოუკიდებლობა - 1991 წლის 9 აპრილი\n" +
                        " კონსტიტუცია მიიღეს 1995 წლის 24 აგვისტოს .  \n"
            ),
            Image(
                R.drawable.england,
                "ინგლისი",
                " მოსახლეობა 55,98 მილიონი \n" +
                        "  დედაქალაქი - ლონდონი\n" +
                        "  სახელმწიფო მდებარეობს დიდი ბრიტანეთის კუნძულის სამხრეთ-აღმოსავლეთ ნაწილზე.\n" +
                        "  საპარლამენტო მმართველობა, კონსტიტუციური მონარქია.\n"
            ),
            Image(
                R.drawable.usa,
                "აშშ",
                " მოსახლეობა 331.9 მილიონი \n" +
                        " დედაქალაქი - ვაშინგტონი \n" +
                        " ქვეყნის ტერიტორიის ძირითადი ნაწილი მდებარეობს ჩრდილოეთ ამერიკის ცენტრალურ ნაწილში.\n" +
                        " საპრეზიდენტო რესპუბლიკა.\n"
            ),
            Image(
                R.drawable.francee,
                "საფრანგეთი",
                "მოსახლეობა 67,75 მილიონი\n" +
                        "დედაქალაქი - პარიზი\n" +
                        " ქვეყანა დასავლეთ ევროპაში, მოიცავს ევროპის ამ ნაწილის, რამდენიმე კუნძულისა და ზღვისიქითა ტერიტორიების ნაწილს.\n" +
                        " მმართველობა - ნახევრად საპრეზიდენტო\n"
            ),
            Image(
                R.drawable.germanyy,
                "გერმანია",
                " მოსახლეობა - 83,2 მილიონი\n" +
                        "  დედაქალაქი - ბერლინი \n" +
                        "   ქვეყანა ცენტრალურ ევროპაში.\n" +
                        "  მმართველობა - საპარლამენტო\n"
            ),
            Image(
                R.drawable.italy,
                "იტალია",
                "მოსახლეობა - 59.11 მილიონი\n" +
                        " დედაქალაქი - რომი \n" +
                        " ქვეყანა მდებარეობს სამხრეთ-დასავლეთ ევროპაში. \n" +
                        " მმართველობა საპარლამენტო, კონსტიტუციური რესპუბლიკა\n"
            ),
            Image(
                R.drawable.spain,
                "ესპანეთი ",
                " მოსახლეობა - 47.42 მილიონი\n" +
                        "   დედაქალაქი - მადრიდი\n" +
                        " სახელმწიფო ევროპის სამხრეთ-დასავლეთით, პირენეს ნახევარკუნძულზე.\n" +
                        " მმართველობა საპარლამენტო, კონსტიტუციური მონარქია\n"
            ),
        )

        val recyclerView = findViewById<RecyclerView>(R.id._imageRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ImageAdapter(this, imageList) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, it)
            startActivity(intent)
        }
    }

    private fun goToMenu() {
        button.setOnClickListener() {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}