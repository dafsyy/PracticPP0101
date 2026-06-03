package com.example.smarthouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthouse.databinding.ItemRoomBinding

class RoomAdapter(
    private val rooms: List<RoomModel>,
    private val onClick: (RoomModel) -> Unit
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(
        private val binding: ItemRoomBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(room: RoomModel) {

            binding.tvRoomName.text = room.roomName
            binding.imgRoom.setImageResource(room.roomIcon)

            binding.root.setOnClickListener {
                onClick(room)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoomViewHolder {

        val binding = ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RoomViewHolder(binding)
    }

    override fun getItemCount(): Int = rooms.size

    override fun onBindViewHolder(
        holder: RoomViewHolder,
        position: Int
    ) {
        holder.bind(rooms[position])
    }
}