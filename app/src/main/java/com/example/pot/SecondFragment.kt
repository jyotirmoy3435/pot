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
import android.util.Log
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
        lateinit var notificationManager: NotificationManager
        var initialAudioMode = 0
        var initialInterruptionFilter = 0

        // on below line we are initializing our audio manager.
        audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // on below line we are getting our current ring tone mode.
        initialAudioMode = audioManager.ringerMode
        initialInterruptionFilter = notificationManager.currentInterruptionFilter
        
        // on below line we are setting text view for the current mode.
        when (initialAudioMode) {
            // on below line we are setting text view as ringer mode for normal ringer mode.
            AudioManager.RINGER_MODE_NORMAL -> Toast.makeText(
                requireActivity(),
                "R_" + initialAudioMode.toString(),
                Toast.LENGTH_SHORT
            ).show()

            // on below line we are setting silent mode for current silent mode.
            AudioManager.RINGER_MODE_SILENT -> Toast.makeText(
                requireActivity(),
                "S_" + initialAudioMode.toString(),
                Toast.LENGTH_SHORT
            ).show()

            // on below line we are setting vibrate mode for current vibrate mode.
            AudioManager.RINGER_MODE_VIBRATE -> Toast.makeText(
                requireActivity(),
                "V_" + initialAudioMode.toString(),
                Toast.LENGTH_SHORT
            ).show()

            // below code will be called when the current mode is not able to detect
            else -> Toast.makeText(requireActivity(), "F", Toast.LENGTH_SHORT).show()
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.dndBtn.setOnClickListener {
            // on below line we are creating a variable for intent.
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted) {
                startActivity(intent)
            }
            else {
                if (!notificationManager.isNotificationPolicyAccessGranted) Toast.makeText(requireActivity(), "NP", Toast.LENGTH_SHORT).show()
                else {
                    if (notificationManager.currentInterruptionFilter == NotificationManager.INTERRUPTION_FILTER_NONE){
                        notificationManager.setInterruptionFilter(initialInterruptionFilter)
                        Toast.makeText(requireActivity(), "!DND", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
                        Toast.makeText(requireActivity(), "DND", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.silenceBtn.setOnClickListener {
            // on below line we are creating a variable for intent.
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted) {
                startActivity(intent)
            }
            else {
                if (!notificationManager.isNotificationPolicyAccessGranted) Toast.makeText(requireActivity(), "NP", Toast.LENGTH_SHORT).show()
                else {
                    if (audioManager.ringerMode < 2){
                        audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                        Toast.makeText(requireActivity(), "NA", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
                        Toast.makeText(requireActivity(), "VSA", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}