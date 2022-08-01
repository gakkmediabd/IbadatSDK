package com.ibadat.sdk.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibadat.sdk.R
import com.ibadat.sdk.adapter.SurahDetailsAdapter
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.data.model.QuranDetailsModel
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.data.restrepo.RetroClient
import com.ibadat.sdk.player.PlayerViewModel
import com.ibadat.sdk.player.STATE
import com.ibadat.sdk.player.data.model.Surah
import com.ibadat.sdk.util.*
import com.ibadat.sdk.views.MyCustomTextView
import kotlinx.android.synthetic.main.wallpaper_anim_list_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

internal class SurahDetailsFragment : BaseFragment() {
    var mSuraIndex: Int = 0
    private lateinit var rvPobitroQuranDetails: RecyclerView
    private lateinit var sbMediaControllerProgress: SeekBar
    private lateinit var ctvSurahCount: MyCustomTextView
    private lateinit var ctvSurahTitle: MyCustomTextView
    private lateinit var ctvCount: MyCustomTextView
    private lateinit var ivImage: ImageView
    private lateinit var quranSuraArray: Array<String>

    private lateinit var ivMiniPlayerPhoto: ImageView
    private lateinit var ctvMiniPlayerTitle: MyCustomTextView
    private lateinit var ivMiniPlayerVolLow: ImageView
    private lateinit var sbAudioVol: SeekBar
    private lateinit var ivMiniPlayerVolHigh: ImageView
    private lateinit var ivMiniPlayerPrev: ImageView
    private lateinit var ivPlay: ImageView
    private lateinit var ivMiniPlayerNext: ImageView
    private lateinit var pbBottomDialog: ProgressBar
    private lateinit var audioManager: AudioManager

    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //        binding = FragmentSurahDetailsBinding.inflate(layoutInflater, container, false)
        return inflater.inflate(R.layout.fragment_surah_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        quranSuraArray = resources.getStringArray(R.array.surah_name)
        initView()
        loadSurah()
        playerControl(mSuraIndex)
        Log.e("SDF", "onViewCreated: $mSuraIndex")
        ivMiniPlayerNext.setOnClickListener {
            playNextSurah()
        }
        ivMiniPlayerPrev.setOnClickListener {
            playPreviousSurah()
        }

        //This Player code will be remove future.
        playerViewModel.playerCurrentPosition.observe(requireActivity()) {
            sbMediaControllerProgress.playerAction(playerViewModel::seekTo)
        }
        playerViewModel.playerCurrentPosition.observe(requireActivity()) {
            sbMediaControllerProgress.playerCurrentPosition(it)
        }
        playerViewModel.playerDuration.observe(requireActivity()) {
            sbMediaControllerProgress.playerDuration(it)
        }
        playerViewModel.playbackState.observe(requireActivity()) {
            ivPlay.playbackState(it)
        }
    }

    private fun playerControl(mSuraIndex: Int) {
        val quranSura = "http://43.240.103.34/ebadattest/ftp/quran/" + (mSuraIndex + 1) + ".mp3"
        playerViewModel.prepare(
            surah = Surah(
                playUrl = quranSura,
                //playUrl = "https://file-examples.com/storage/feb8f98f1d627c0dc94b8cf/2017/11/file_example_MP3_700KB.mp3",
                title = quranSuraArray[mSuraIndex] ?: "Unknown Surah"
            ), playWhenReady = false
        )

        audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        ivPlay.setOnClickListener {
            playerViewModel.togglePlayPause()
        }

        sbAudioVol.progress =
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        sbAudioVol.max =
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        sbAudioVol.setOnSeekBarChangeListener(object :
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
        pbBottomDialog.visibility = View.VISIBLE
        val api: ApiService = RetroClient.getQuranApiService()!!
        val call = api.getSurahDetails(mSuraIndex + 1, "bn")
        Log.e("SDF", "loadSurah: " + mSuraIndex)
        call.enqueue(object : Callback<QuranDetailsModel> {
            override fun onResponse(
                call: Call<QuranDetailsModel>,
                response: Response<QuranDetailsModel>
            ) {
                pbBottomDialog.visibility = View.GONE
                if (response.isSuccessful && response.code() == 200) {
                    val quranDetailsModel: QuranDetailsModel? = response.body()
                    if (quranDetailsModel != null) {
                        loadRV(quranDetailsModel)
                    }
                }
            }

            override fun onFailure(call: Call<QuranDetailsModel>, t: Throwable) {
                pbBottomDialog.visibility = View.GONE
            }
        })
    }

    private fun loadRV(quranDetailsModel: QuranDetailsModel) {
        if (quranDetailsModel.data != null) {
            rvPobitroQuranDetails.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val adapter = SurahDetailsAdapter(
                requireContext(),
                quranDetailsModel.data
            )
            rvPobitroQuranDetails.adapter = CustomAnimation.getAnimatedRecyclerView(adapter)
            rvPobitroQuranDetails.isNestedScrollingEnabled = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateViewPerSurah() {
        ctvSurahCount.text = LanguageConverter.getDateInBangla((mSuraIndex + 1).toString())
        ctvSurahTitle.text = quranSuraArray[mSuraIndex]
        ctvCount.text = getString(R.string.total_ayat) + " " + LanguageConverter.getDateInBangla(
            GlobalVar.AyetCount[mSuraIndex]
        )
        ctvMiniPlayerTitle.text = quranSuraArray[mSuraIndex]
    }

    private fun initView() {
        val bundle = this.arguments
        if (bundle != null) {
            mSuraIndex = bundle.getInt("position")
        }
        rvPobitroQuranDetails = requireView().findViewById(R.id.rv_pobitro_quran_details)
        sbMediaControllerProgress = requireView().findViewById(R.id.sb_media_controller_progress)
        ctvSurahCount = requireView().findViewById(R.id.ctv_surah_count)
        ctvSurahTitle = requireView().findViewById(R.id.ctv_surah_title)
        ctvCount = requireView().findViewById(R.id.ctv_count)
        pbBottomDialog = requireView().findViewById(R.id.pb_bottom_dialog)
        ivImage = requireView().findViewById(R.id.iv_image)
        ivImage.setImageURI(
            Util.getUriFromPath(
                requireContext(),
                AppConstantUtils.drawable_hdpi + "art.png"
            )
        )
        val includeMiniPlayer: RelativeLayout = requireView().findViewById(R.id.include_mini_player)
        ivMiniPlayerPhoto = includeMiniPlayer.findViewById(R.id.iv_mini_player_photo)
        ctvMiniPlayerTitle = requireView().findViewById(R.id.ctv_mini_player_title)
        ivMiniPlayerVolLow = includeMiniPlayer.findViewById(R.id.iv_mini_player_vol_low)
        sbAudioVol = includeMiniPlayer.findViewById(R.id.sb_audio_vol)
        ivMiniPlayerVolHigh = includeMiniPlayer.findViewById(R.id.iv_mini_player_vol_high)
        ivMiniPlayerPrev = requireView().findViewById(R.id.iv_mini_player_prev)
        ivPlay = includeMiniPlayer.findViewById(R.id.iv_play)
        ivMiniPlayerNext = includeMiniPlayer.findViewById(R.id.iv_mini_player_next)

        updateViewPerSurah()
    }

    private fun playNextSurah() {
        var mSuraIndex: Int
        this.mSuraIndex = if (this.mSuraIndex.also { mSuraIndex = it } >= 113) 0 else mSuraIndex + 1
        updateViewPerSurah()
        loadSurah()
        Log.e("SDF", "playNextSurah: $mSuraIndex")
        playerControl2(mSuraIndex + 1)
        return
    }

    private fun playPreviousSurah() {
        Log.e("SDF", "playPreviousSurah: $mSuraIndex")
        if (mSuraIndex != 0) {
            mSuraIndex -= 1
            updateViewPerSurah()
            loadSurah()
            playerControl2(mSuraIndex)
            return
        }
    }

    private fun playerControl2(mSuraIndex: Int) {
        val quranSura = "http://43.240.103.34/ebadattest/ftp/quran/" + (mSuraIndex + 1) + ".mp3"
        playerViewModel.prepare(
            surah = Surah(
                playUrl = quranSura,
                //playUrl = "https://file-examples.com/storage/feb8f98f1d627c0dc94b8cf/2017/11/file_example_MP3_700KB.mp3",
                title = quranSuraArray.get(mSuraIndex) ?: "Unknown Surah"
            ), playWhenReady = true
        )

        audioManager = requireActivity().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        ivPlay.setOnClickListener {
            playerViewModel.togglePlayPause()
        }
        sbAudioVol.progress =
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        sbAudioVol.max =
            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        sbAudioVol.setOnSeekBarChangeListener(object :
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


