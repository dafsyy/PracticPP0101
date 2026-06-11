package com.example.smarthouse

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthouse.databinding.ActivityAddRoomBinding

class AddRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRoomBinding
    private var customIcons = mutableListOf<ImageView>()

    private val createTypeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                refreshCustomTypes()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        setupStandardRooms()

        binding.btnOther.setOnClickListener {
            val intent = Intent(this, CreateRoomTypeActivity::class.java)
            createTypeLauncher.launch(intent)
        }

        refreshCustomTypes()
        resetIcons()
    }

    private fun setupStandardRooms() {
        // Навешиваем клик на весь контейнер (иконка + текст)
        binding.containerLivingRoom.setOnClickListener { selectRoom("living_room", binding.imgLivingRoom) }
        binding.containerKitchen.setOnClickListener { selectRoom("kitchen", binding.imgKitchen) }
        binding.containerBathroom.setOnClickListener { selectRoom("bathroom", binding.imgBathroom) }
        binding.containerOffice.setOnClickListener { selectRoom("office", binding.imgOffice) }
        binding.containerBedroom.setOnClickListener { selectRoom("bedroom", binding.imgBedroom) }
        binding.containerHall.setOnClickListener { selectRoom("hall", binding.imgHall) }
    }

    private fun refreshCustomTypes() {
        binding.gridCustomTypes.removeAllViews()
        customIcons.clear()

        val density = resources.displayMetrics.density
        val iconSize = (70 * density).toInt()

        for (type in CustomRoomStorage.roomTypes) {
            // Создаем контейнер вертикальный
            val container = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding((8 * density).toInt(), (8 * density).toInt(), (8 * density).toInt(), (8 * density).toInt())
                background = android.graphics.drawable.ColorDrawable(Color.TRANSPARENT)
                isClickable = true
                isFocusable = true
                // Эффект нажатия
                val outValue = android.util.TypedValue()
                theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                setBackgroundResource(outValue.resourceId)
            }

            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(iconSize, iconSize)
                setImageResource(type.grayIcon)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
            }

            val textView = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = type.name
                textSize = 12f
                setTextColor(Color.parseColor("#303030"))
            }

            container.addView(imageView)
            container.addView(textView)

            // Клик по всему контейнеру
            container.setOnClickListener {
                selectCustomRoom(type, imageView)
            }

            binding.gridCustomTypes.addView(container)
            customIcons.add(imageView)
        }
    }

    private fun selectRoom(type: String, view: ImageView) {
        resetIcons()
        
        val activeRes = when(type) {
            "living_room" -> R.drawable.ic_living_room
            "kitchen" -> R.drawable.ic_kitchen
            "bathroom" -> R.drawable.ic_bathroom
            "office" -> R.drawable.ic_office
            "bedroom" -> R.drawable.ic_bedroom
            "hall" -> R.drawable.ic_hall
            else -> return
        }
        view.setImageResource(activeRes)
        
        val typeNameRus = when(type) {
            "living_room" -> "Гостиная"
            "kitchen" -> "Кухня"
            "bathroom" -> "Ванная"
            "office" -> "Кабинет"
            "bedroom" -> "Спальня"
            "hall" -> "Зал"
            else -> ""
        }
        showRoomNameDialog(typeNameRus, type, activeRes)
    }

    private fun selectCustomRoom(type: RoomTypeModel, view: ImageView) {
        resetIcons()
        view.setImageResource(type.icon)
        showRoomNameDialog(type.name, "custom", type.icon)
    }

    private fun resetIcons() {
        binding.imgLivingRoom.setImageResource(R.drawable.ic_living_room_gray)
        binding.imgKitchen.setImageResource(R.drawable.ic_kitchen_gray)
        binding.imgBathroom.setImageResource(R.drawable.ic_bathroom_gray)
        binding.imgOffice.setImageResource(R.drawable.ic_office_gray)
        binding.imgBedroom.setImageResource(R.drawable.ic_bedroom_gray)
        binding.imgHall.setImageResource(R.drawable.ic_hall_gray)

        CustomRoomStorage.roomTypes.forEachIndexed { index, type ->
            if (index < customIcons.size) {
                customIcons[index].setImageResource(type.grayIcon)
            }
        }
    }

    private fun showRoomNameDialog(typeName: String, typeKey: String, iconRes: Int) {
        val editText = android.widget.EditText(this)
        editText.hint = "Напр. Моя $typeName"

        AlertDialog.Builder(this)
            .setTitle("Название комнаты")
            .setMessage("Тип: $typeName")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val nameInput = editText.text.toString().trim()
                if (nameInput.isNotEmpty()) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("ROOM_NAME", "$typeName $nameInput")
                    resultIntent.putExtra("ROOM_TYPE", typeKey)
                    resultIntent.putExtra("ROOM_ICON", iconRes)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
            .setNegativeButton("Отмена") { _, _ -> resetIcons() }
            .show()
    }
}
