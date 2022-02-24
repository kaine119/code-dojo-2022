package team.stonks.buzoku.ui.promptHelpRequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import team.stonks.buzoku.R
import team.stonks.buzoku.databinding.ActivityMainBinding
import team.stonks.buzoku.databinding.ActivityPromptRequestBinding

class PromptHelpRequestActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPromptRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prompt_request)


    }
}