package com.example.pot

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pot.databinding.FragmentSecondBinding
import android.widget.Toast
import android.media.AudioManager
import androidx.core.content.ContextCompat.getSystemService

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var audioManager: AudioManager
        var currentAudioMode = 0

        // on below line we are initializing our audio manager.
        audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        
        // on below line we are getting our current ring tone mode.
        currentAudioMode = audioManager.ringerMode;
        
        // on below line we are setting text view for the current mode.
        when (currentAudioMode) {
            // on below line we are setting text view as ringer mode for normal ringer mode.
            AudioManager.RINGER_MODE_NORMAL -> Toast.makeText(
                requireActivity(),
                "R",
                Toast.LENGTH_SHORT
            ).show()

            // on below line we are setting silent mode for current silent mode.
            AudioManager.RINGER_MODE_SILENT -> Toast.makeText(
                requireActivity(),
                "S",
                Toast.LENGTH_SHORT
            ).show()

            // on below line we are setting vibrate mode for current vibrate mode.
            AudioManager.RINGER_MODE_VIBRATE -> Toast.makeText(
                requireActivity(),
                "V",
                Toast.LENGTH_SHORT
            ).show()

            // below code will be called when the current mode is not able to detect
            else -> Toast.makeText(requireActivity(), "F", Toast.LENGTH_SHORT).show()
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.dndBtn.setOnClickListener {
            Toast.makeText(requireActivity(), "You clicked me.", Toast.LENGTH_SHORT).show()
        }
        binding.silenceBtn.setOnClickListener {
            val notificationManager: NotificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // on below line we are creating a variable for intent.
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted) {
                startActivity(intent)
            }
            else {
                Toast.makeText(requireActivity(), "NP", Toast.LENGTH_SHORT).show()
                audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT

                // on below line we are displaying a simple toast message.
                Toast.makeText(requireActivity(), "SA", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}