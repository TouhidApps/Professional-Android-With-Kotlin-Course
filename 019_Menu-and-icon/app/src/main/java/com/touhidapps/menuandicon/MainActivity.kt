package com.touhidapps.menuandicon

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.touhidapps.menuandicon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        title = "My App Title"

        registerForContextMenu(binding.tvMain)

    } // onCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menuSettings -> {
                Toast.makeText(this, "Clicked on Settings", Toast.LENGTH_SHORT).show()
            }
            R.id.menuAboutMe -> {
                Toast.makeText(this, "Clicked on About me", Toast.LENGTH_SHORT).show()
            }
            R.id.menuAboutCompany -> {
                Toast.makeText(this, "Clicked on About Company", Toast.LENGTH_SHORT).show()
            }
            R.id.menuExit -> {
                Toast.makeText(this, "Clicked on Exit", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menuAboutMe -> {
                Toast.makeText(this, "Click on About Me", Toast.LENGTH_SHORT).show()
            }
            R.id.menuExit -> {
                Toast.makeText(this, "Click on Exit", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        return super.onContextItemSelected(item)
    }

}