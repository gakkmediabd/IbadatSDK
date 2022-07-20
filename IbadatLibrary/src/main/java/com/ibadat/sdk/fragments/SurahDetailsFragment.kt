package com.ibadat.sdk.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.SurahDetailsAdapter
import com.ibadat.sdk.data.model.QuranDetailsModel
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.data.restrepo.RetroClient
import com.ibadat.sdk.databinding.FragmentSurahDetailsBinding
import com.ibadat.sdk.player.PlayerViewModel
import com.ibadat.sdk.player.data.model.Surah
import com.ibadat.sdk.util.CustomAnimation
import com.ibadat.sdk.util.GlobalVar
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.util.Util
import com.ibadat.sdk.views.MyCustomTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class SurahDetailsFragment : Fragment() {
    var mSuraIndex: Int = 0
    lateinit var toolbar: Toolbar
    var ayatListRV: RecyclerView? = null
    var txtCount: MyCustomTextView? = null
    var txtTitle: MyCustomTextView? = null
    var txt1: MyCustomTextView? = null
    private lateinit var imageView: ImageView
    var btnNext: ImageView? = null
    var quranSuraArray: Array<String>? = null
    var sb_mediacontroller_progress : SeekBar? = null

    private lateinit var playerViewModel: PlayerViewModel
    private var ivMiniPlayerPhoto: ImageView? = null
    private var ctvMiniPlayerTitle: MyCustomTextView? = null
    private var ivMiniPlayerVolLow: ImageView? = null
    private var sbAudioVol: SeekBar? = null
    private var ivMiniPlayerVolHigh: ImageView? = null
    private var ivMiniPlayerPrev: ImageView? = null
    private var ivBtnPlay: ImageView? = null
    private var ivMiniPlayerNext: ImageView? = null

    //    private lateinit var binding: FragmentSurahDetailsBinding
    private var progressBar: ProgressBar? = null
    private lateinit var audioManager: AudioManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_surah_details, container, false)
    }

    private fun initView() {
        val bundle = this.arguments
        if (bundle != null) {
            mSuraIndex = bundle.getInt("position")
        }
        ayatListRV = requireView().findViewById(R.id.PobitroQuranDetailsList)
        txtCount = requireView().findViewById(R.id.ctv_surahCount)
        txtTitle = requireView().findViewById(R.id.ctv_surah_title)
        txt1 = requireView().findViewById(R.id.count)
        btnNext = requireView().findViewById(R.id.mini_player_next)
        progressBar = requireView().findViewById(R.id.bottomProgressDialog)
        imageView = requireView().findViewById(R.id.iv_image)
        imageView.setImageURI(Util.getUriFromPath(requireContext(), "drawable-hdpi/art.png"))

//        Glide.with(requireContext())
//            .load("https://mygp.ibadat.co/content/sdk/art.png")
//            .diskCacheStrategy(
//                DiskCacheStrategy.ALL).into(imageView)
        ivMiniPlayerPhoto = requireView().findViewById(R.id.iv_mini_player_photo)
        ctvMiniPlayerTitle = requireView().findViewById(R.id.ctv_mini_player_title)
        ivMiniPlayerVolLow = requireView().findViewById(R.id.iv_mini_player_vol_low)
        sbAudioVol = requireView().findViewById(R.id.sb_audioVol)
        ivMiniPlayerVolHigh = requireView().findViewById(R.id.iv_mini_player_vol_high)
        ivMiniPlayerPrev = requireView().findViewById(R.id.iv_mini_player_prev)
        ivBtnPlay = requireView().findViewById(R.id.iv_btnPlay)
        ivMiniPlayerNext = requireView().findViewById(R.id.iv_mini_player_next)


        updateViewPerSurah()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        quranSuraArray = resources.getStringArray(R.array.surah_name)
        initView()
        loadSurah()
        playerControl(mSuraIndex)
        btnNext?.setOnClickListener {
            playNextSurah()
        }
        ivMiniPlayerPrev?.setOnClickListener {
            playPreviousSurah()
        }
    }

    private fun playerControl(mSuraIndex: Int) {
        val quranSura = "http://43.240.103.34/ebadattest/ftp/quran/" + (mSuraIndex + 1) + ".mp3"
        playerViewModel.prepare(
            surah = Surah(
                playUrl = quranSura,
                //playUrl = "https://file-examples.com/storage/feb8f98f1d627c0dc94b8cf/2017/11/file_example_MP3_700KB.mp3",
                title = quranSuraArray?.get(mSuraIndex) ?: "Unknown Surah"
            ), playWhenReady = false
        )

        audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        ivBtnPlay!!.setOnClickListener {
            playerViewModel.togglePlayPause()
        }
        sbAudioVol!!.progress =
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        sbAudioVol!!.max =
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        sbAudioVol!!.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    private fun setupViewModel() {
        playerViewModel = ViewModelProvider(requireActivity())[PlayerViewModel::class.java]
//        binding.lifecycleOwner = requireActivity()
//        binding.viewModel = playerViewModel
    }

    private fun loadSurah() {
        progressBar!!.visibility = View.VISIBLE
        val api: ApiService = RetroClient.getQuranApiService()!!
        val call = api.getSurahDetails(mSuraIndex + 1, "bn")
        call.enqueue(object : Callback<QuranDetailsModel> {
            override fun onResponse(
                call: Call<QuranDetailsModel>,
                response: Response<QuranDetailsModel>
            ) {
                progressBar?.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val quranDetailsModel: QuranDetailsModel? = response.body()
                    if (quranDetailsModel != null) {
                        loadRV(quranDetailsModel)
                    }
                }
            }

            override fun onFailure(call: Call<QuranDetailsModel>, t: Throwable) {
                progressBar?.visibility = View.GONE
            }
        })
    }

    private fun loadRV(quranDetailsModel: QuranDetailsModel) {
        if (quranDetailsModel.data != null) {
            ayatListRV!!.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val adapter = SurahDetailsAdapter(
                requireContext(),
                quranDetailsModel.data
            )
            ayatListRV!!.adapter = CustomAnimation.getAnimatedRecyclerView(adapter)
            ayatListRV!!.isNestedScrollingEnabled = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateViewPerSurah() {
        txtCount?.text = LanguageConverter.getDateInBangla((mSuraIndex + 1).toString())
        txtTitle!!.text = quranSuraArray?.get(mSuraIndex)
        txt1!!.text = getString(R.string.total_ayat) + " " + LanguageConverter.getDateInBangla(
            GlobalVar.AyetCount[mSuraIndex]
        )
        ctvMiniPlayerTitle!!.text = quranSuraArray?.get(mSuraIndex)
    }


    private fun playNextSurah() {
        var mSuraIndex: Int
        this.mSuraIndex = if (this.mSuraIndex.also { mSuraIndex = it } >= 113) 0 else mSuraIndex + 1
        updateViewPerSurah()
        loadSurah()
        playerControl2(mSuraIndex + 1)
        return
    }

    private fun playPreviousSurah() {
        mSuraIndex -= 1
        updateViewPerSurah()
        loadSurah()
        playerControl2(mSuraIndex)
        return
    }

    private fun playerControl2(mSuraIndex: Int) {
        val quranSura = "http://43.240.103.34/ebadattest/ftp/quran/" + (mSuraIndex + 1) + ".mp3"
        playerViewModel.prepare(
            surah = Surah(
                playUrl = quranSura,
                //playUrl = "https://file-examples.com/storage/feb8f98f1d627c0dc94b8cf/2017/11/file_example_MP3_700KB.mp3",
                title = quranSuraArray?.get(mSuraIndex) ?: "Unknown Surah"
            ), playWhenReady = true
        )

        audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        ivBtnPlay!!.setOnClickListener {
            playerViewModel.togglePlayPause()
        }
        sbAudioVol!!.progress =
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        sbAudioVol!!.max =
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        sbAudioVol!!.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}


