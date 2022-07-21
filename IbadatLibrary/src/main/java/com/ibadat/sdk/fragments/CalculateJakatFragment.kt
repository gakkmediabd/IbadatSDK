package com.ibadat.sdk.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ibadat.sdk.R
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.call_back.BaseCallBack
//import com.ibadat.sdk.databinding.FragmentCalculateJakatBinding
import com.ibadat.sdk.util.handleClickEvent
import com.ibadat.sdk.views.MyCustomTextView
import kotlinx.android.synthetic.main.fragment_calculate_jakat.view.*
import java.text.DecimalFormat


internal class CalculateJakatFragment : BaseFragment() {
//    private lateinit var binding: FragmentCalculateJakatBinding

    private lateinit var clCalculate: ConstraintLayout
    private lateinit var acivBgTotalAsset: AppCompatImageView
    private lateinit var ctvTitle: MyCustomTextView
    private lateinit var ivImageView: ImageView
    private lateinit var ctvTotalAsset: MyCustomTextView
    private lateinit var ctvTitleJakat: MyCustomTextView
    private lateinit var ivColon: ImageView
    private lateinit var ctvTotalJakat: MyCustomTextView
    private lateinit var ctvTitleTotalAsset: MyCustomTextView

    //include_nagad_taka_header
    private lateinit var ctvNagadTakaHeaderTitleHeader: MyCustomTextView

    //include_nagad_taka_content
    private lateinit var ctvNagadTakaContentTitle: MyCustomTextView
    private lateinit var tv_nagad_taka_content_symbol: TextView
    private lateinit var acet_nagad_taka_content_amount: AppCompatEditText
    private lateinit var gl_nagad_taka_content_guideline3: Guideline

    //include_bank_nagad_taka_content
    private lateinit var ctv_bank_nagad_taka_content_title: MyCustomTextView
    private lateinit var tv_bank_nagad_taka_content_symbol: TextView
    private lateinit var acet_bank_nagad_taka_content_amount: AppCompatEditText
    private lateinit var gl_bank_nagad_taka_content_guideline3: Guideline

    //include_ornament_amt_header
    private lateinit var ctv_ornament_amt_header_title_header: MyCustomTextView

    //include_gold_amt_content
    private lateinit var ctv_gold_amt_content_content_title: MyCustomTextView
    private lateinit var tv_gold_amt_content_symbol: TextView
    private lateinit var acet_gold_amt_content_amount: AppCompatEditText
    private lateinit var gl_gold_amt_content_guideline3: Guideline

    //include_silver_amt_content
    private lateinit var ctv_silver_amt_content_content_title: MyCustomTextView
    private lateinit var tv_silver_amt_content_symbol: TextView
    private lateinit var acet_silver_amt_content_amount: AppCompatEditText
    private lateinit var gl_silver_amt_content_guideline3: Guideline

    //include_investment_amt_header
    private lateinit var ctv_investment_amt_header_title_header: MyCustomTextView

    //include_share_market_content
    private lateinit var ctv_share_market_content_content_title: MyCustomTextView
    private lateinit var tv_share_market_content_symbol: TextView
    private lateinit var acet_share_market_content_amount: AppCompatEditText
    private lateinit var gl_share_market_content_guideline3: Guideline

    //include_other_invest_content
    private lateinit var ctv_other_invest_content_content_title: MyCustomTextView
    private lateinit var tv_other_invest_content_symbol: TextView
    private lateinit var acet_other_invest_content_amount: AppCompatEditText
    private lateinit var gl_other_invest_content_guideline3: Guideline

    //include_asset_header
    private lateinit var ctv_asset_header_title_header: MyCustomTextView

    //include_house_rent_content
    private lateinit var ctv_house_rent_content_content_title: MyCustomTextView
    private lateinit var tv_house_rent_content_symbol: TextView
    private lateinit var acet_house_rent_content_amount: AppCompatEditText
    private lateinit var gl_house_rent_content_guideline3: Guideline

    //include_asset_content
    private lateinit var ctv_asset_content_content_title: MyCustomTextView
    private lateinit var tv_asset_content_symbol: TextView
    private lateinit var acet_asset_content_amount: AppCompatEditText
    private lateinit var gl_asset_content_guideline3: Guideline


    //include_business_header
    private lateinit var ctv_business_header_title_header: MyCustomTextView

    //include_nogod_busines_content
    private lateinit var ctv_nogod_busines_content_content_title: MyCustomTextView
    private lateinit var tv_nogod_busines_content_symbol: TextView
    private lateinit var acet_nogod_busines_content_amount: AppCompatEditText
    private lateinit var gl_nogod_busines_content_guideline3: Guideline

    //include_product_content
    private lateinit var ctv_product_content_content_title: MyCustomTextView
    private lateinit var tv_product_content_symbol: TextView
    private lateinit var acet_product_content_amount: AppCompatEditText
    private lateinit var gl_product_content_guideline3: Guideline


    //include_other_header
    private lateinit var ctv_other_header_title_header: MyCustomTextView

    //include_pension_content
    private lateinit var ctv_pension_content_content_title: MyCustomTextView
    private lateinit var tv_pension_content_symbol: TextView
    private lateinit var acet_pension_content_amount: AppCompatEditText
    private lateinit var gl_pension_content_guideline3: Guideline

    //include_loan_content
    private lateinit var ctv_loan_content_content_title: MyCustomTextView
    private lateinit var tv_loan_content_symbol: TextView
    private lateinit var acet_loan_content_amount: AppCompatEditText
    private lateinit var gl_loan_content_guideline3: Guideline

    //include_capital_content
    private lateinit var ctv_capital_content_title_header: MyCustomTextView

    //include_farming_header
    private lateinit var ctv_farming_header_content_title: MyCustomTextView
    private lateinit var tv_farming_header_symbol: TextView
    private lateinit var acet_farming_header_amount: AppCompatEditText
    private lateinit var gl_farming_header_guideline3: Guideline

    //include_farming_content
    private lateinit var ctv_farming_content_content_title: MyCustomTextView
    private lateinit var tv_farming_content_symbol: TextView
    private lateinit var acet_farming_content_amount: AppCompatEditText
    private lateinit var gl_farming_content_guideline3: Guideline


    //include_liability_header
    private lateinit var ctv_liability_header_title_header: MyCustomTextView

    //include_credit_card_content
    private lateinit var ctv_credit_card_content_content_title: MyCustomTextView
    private lateinit var tv_credit_card_content_symbol: TextView
    private lateinit var acet_credit_card_content_amount: AppCompatEditText
    private lateinit var gl_credit_card_content_guideline3: Guideline

    //include_car_content
    private lateinit var ctv_car_content_content_title: MyCustomTextView
    private lateinit var tv_car_content_symbol: TextView
    private lateinit var acet_car_content_amount: AppCompatEditText
    private lateinit var gl_car_content_guideline3: Guideline


    //include_business_payment_content
    private lateinit var ctv_business_payment_content_title_header: MyCustomTextView

    //include_family_loan_content
    private lateinit var ctv_family_loan_content_content_title: MyCustomTextView
    private lateinit var tv_family_loan_content_symbol: TextView
    private lateinit var acet_family_loan_content_amount: AppCompatEditText
    private lateinit var gl_family_loan_content_guideline3: Guideline

    //include_other_loan_content
    private lateinit var ctv_other_loan_content_content_title: MyCustomTextView
    private lateinit var tv_other_loan_content_symbol: TextView
    private lateinit var acet_other_loan_content_amount: AppCompatEditText
    private lateinit var gl_other_loan_content_guideline3: Guideline


    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_calculate_jakat, container, false)
        viewInitialize(view)

        return view
    }

    private fun viewInitialize(view: View) {
        clCalculate = view.findViewById(R.id.cl_calculate)
        acivBgTotalAsset = view.findViewById(R.id.aciv_bg_total_asset)
        ctvTitle = view.findViewById(R.id.ctv_title)
        ivImageView = view.findViewById(R.id.iv_imageView)
        ctvTotalAsset = view.findViewById(R.id.ctv_total_asset)
        ctvTitleJakat = view.findViewById(R.id.ctv_title_jakat)
        ivColon = view.findViewById(R.id.iv_colon)
        ctvTotalJakat = view.findViewById(R.id.ctv_total_jakat)

        ctvTitleTotalAsset = view.findViewById(R.id.ctv_title_total_asset)

        val includeNagadTakaHeader: ConstraintLayout =
            view.findViewById(R.id.include_nagad_taka_header)

        val includeNagadTakaContent: ConstraintLayout =
            view.findViewById(R.id.include_nagad_taka_content)

        val includeBankNagadTakaContent: ConstraintLayout =
            view.findViewById(R.id.include_bank_nagad_taka_content)

        val includeOrnamentAmtHeader: ConstraintLayout =
            view.findViewById(R.id.include_ornament_amt_header)

        val includeGoldAmtContent: ConstraintLayout =
            view.findViewById(R.id.include_gold_amt_content)

        val includeSilverAmtContent: ConstraintLayout =
            view.findViewById(R.id.include_silver_amt_content)

        val includeInvestmentAmtHeader: ConstraintLayout =
            view.findViewById(R.id.include_investment_amt_header)

        val includeShareMarketContent: ConstraintLayout =
            view.findViewById(R.id.include_share_market_content)

        val includeOtherInvestContent: ConstraintLayout =
            view.findViewById(R.id.include_other_invest_content)

        val includeAssetHeader: ConstraintLayout =
            view.findViewById(R.id.include_asset_header)

        val includeHouseRentContent: ConstraintLayout =
            view.findViewById(R.id.include_house_rent_content)

        val includeAssetContent: ConstraintLayout =
            view.findViewById(R.id.include_asset_content)

        val includeBusinessHeader: ConstraintLayout =
            view.findViewById(R.id.include_business_header)

        val includeNogodBusinesContent: ConstraintLayout =
            view.findViewById(R.id.include_nogod_busines_content)

        val includeProductContent: ConstraintLayout =
            view.findViewById(R.id.include_product_content)

        val includeOtherHeader: ConstraintLayout =
            view.findViewById(R.id.include_other_header)

        val includePensionContent: ConstraintLayout =
            view.findViewById(R.id.include_pension_content)

        val includeLoanContent: ConstraintLayout =
            view.findViewById(R.id.include_loan_content)

        val includeCapitalContent: ConstraintLayout =
            view.findViewById(R.id.include_capital_content)

        val includeFarmingHeader: ConstraintLayout =
            view.findViewById(R.id.include_farming_header)

        val includeFarmingContent: ConstraintLayout =
            view.findViewById(R.id.include_farming_content)

        val includeLiabilityHeader: ConstraintLayout =
            view.findViewById(R.id.include_liability_header)

        val includeCreditCardContent: ConstraintLayout =
            view.findViewById(R.id.include_credit_card_content)

        val includeCarContent: ConstraintLayout =
            view.findViewById(R.id.include_car_content)

        val includeBusinessPaymentContent: ConstraintLayout =
            view.findViewById(R.id.include_business_payment_content)

        val includeFamilyLoanContent: ConstraintLayout =
            view.findViewById(R.id.include_family_loan_content)

        val includeOtherLoanContent: ConstraintLayout =
            view.findViewById(R.id.include_other_loan_content)

        val btnSave: Button =
            view.findViewById(R.id.btn_save)


        //include_nagad_taka_content
//        ctvNagadTakaContentTitle = view.findViewById(R.id.ctv_content_title)
//        tv_nagad_taka_content_symbol = view.findViewById(R.id.tv_symbol)
//        acet_nagad_taka_content_amount = view.findViewById(R.id.acet_amount)
//        gl_nagad_taka_content_guideline3: Guideline


//        ctv_bank_nagad_taka_content_title = view.findViewById(R.id.ctv_content_title)
//        ctv_bank_nagad_taka_content_title.text = "1111"
//        ctv_bank_nagad_taka_content_title: MyCustomTextView
//        tv_bank_nagad_taka_content_symbol: TextView
//        acet_bank_nagad_taka_content_amount: AppCompatEditText
//        gl_bank_nagad_taka_content_guideline3: Guideline
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.layoutNagadTakaHeader.tvTitleHeader.text =
//            getText(R.string.title_nogod_taka)
//        binding.layoutNagadTakacontent.contentTitle.text =
//            getText(R.string.title_nogod_taka)
//        binding.layoutBankNagadTakacontent.contentTitle.text =
//            getText(R.string.title_bank_nogod_taka)
//        binding.layoutOrnamentAmtHeader.tvTitleHeader.text =
//            getText(R.string.title_ornament_header)
//        binding.layoutGoldAmtcontent.contentTitle.text =
//            getText(R.string.text_gold_amount)
//        binding.layoutSilverAmtcontent.contentTitle.text =
//            getText(R.string.text_silver_amount)
//        binding.layoutInvestmentAmtHeader.tvTitleHeader.text =
//            getText(R.string.title_investment)
//        binding.layoutShareMarketcontent.contentTitle.text =
//            getText(R.string.text_share_market)
//        binding.layoutOtherInvestcontent.contentTitle.text =
//            getText(R.string.text_other_investment)
//        binding.layoutAssetHeader.tvTitleHeader.text = getText(R.string.title_asset)
//        binding.layoutHouseRentcontent.contentTitle.text =
//            getText(R.string.text_house_rent)
//        binding.layoutAssetcontent.contentTitle.text = getText(R.string.title_asset)
//        binding.layoutBusinessHeader.tvTitleHeader.text = getText(R.string.title_business)
//        binding.layoutNogodBusinescontent.contentTitle.text =
//            getText(R.string.text_nogod_business)
//        binding.layoutProductcontent.contentTitle.text = getText(R.string.text_product)
//        binding.layoutOtherHeader.tvTitleHeader.text = getText(R.string.title_other)
//        binding.layoutPensioncontent.contentTitle.text = getText(R.string.text_pension)
//        binding.layoutLoancontent.contentTitle.text = getText(R.string.text_family_loan)
//        binding.layoutCapitalcontent.contentTitle.text =
//            getText(R.string.text_other_capital)
//        binding.layoutFarmingHeader.tvTitleHeader.text = getText(R.string.title_farming)
//        binding.layoutFarmingcontent.contentTitle.text =
//            getText(R.string.text_taka_amount)
//        binding.layoutLiabilityHeader.tvTitleHeader.text =
//            getText(R.string.title_lialibility)
//        binding.layoutLiabilityHeader.tvTitleHeader.setTextColor(
//            ContextCompat.getColor(
//                requireContext(),
//                R.color.red
//            )
//        )
//
//        binding.layoutCreditCardcontent.contentTitle.text =
//            getText(R.string.text_credit_card)
//        binding.layoutCreditCardcontent.tvSymbol.text
//
//        binding.layoutCarcontent.contentTitle.text = getText(R.string.text_car_payment)
//        binding.layoutBusinessPaymentcontent.contentTitle.text =
//            getText(R.string.text_business_payment)
//        binding.layoutFamilyLoancontent.contentTitle.text =
//            getText(R.string.text_family_loan_liability)
//        binding.layoutOtherLoancontent.contentTitle.text =
//            requireContext().getText(R.string.text_other_loan)
//        binding.btnSave.handleClickEvent {
//            binding.constraint.visibility = View.VISIBLE
//            val nogodtakaText = binding.layoutNagadTakacontent.etAmount.text
//            val nogodtakaBankText = binding.layoutBankNagadTakacontent.etAmount.text
//            val goldText = binding.layoutGoldAmtcontent.etAmount.text
//            val silverText = binding.layoutSilverAmtcontent.etAmount.text
//            val shareMarketText = binding.layoutShareMarketcontent.etAmount.text
//            val otherInvestmentText = binding.layoutOtherInvestcontent.etAmount.text
//            val houseRentText = binding.layoutHouseRentcontent.etAmount.text
//            val assetText = binding.layoutAssetcontent.etAmount.text
//            val nogodbusinessText = binding.layoutNogodBusinescontent.etAmount.text
//            val productText = binding.layoutProductcontent.etAmount.text
//            val pensionText = binding.layoutPensioncontent.etAmount.text
//            val loanText = binding.layoutLoancontent.etAmount.text
//            val otherCapitalText = binding.layoutOtherLoancontent.etAmount.text
//            val farmingText = binding.layoutFarmingcontent.etAmount.text
//            val creditCardText = binding.layoutCreditCardcontent.etAmount.text
//            val carPaymentText = binding.layoutCarcontent.etAmount.text
//            val businessPaymentText = binding.layoutBusinessPaymentcontent.etAmount.text
//            val familyLoanText = binding.layoutFamilyLoancontent.etAmount.text
//            val otherLoanText = binding.layoutOtherLoancontent.etAmount.text


        var nogodtakaAmount = 0.0
        var nogodtakaBankAmount = 0.0
        var goldAmount = 0.0
        var silverAmount = 0.0
        var shareMarketAmount = 0.0
        var otherInvestmentAmount = 0.0
        var houseRentAmount = 0.0
        var assetAmount = 0.0
        var nogodbusinessAmount = 0.0
        var productAmount = 0.0
        var pensionAmount = 0.0
        var loanAmount = 0.0
        var otherCapitalAmount = 0.0
        var farmingAmount = 0.0
        var creditCardAmount = 0.0
        var carPaymentAmount = 0.0
        var businessPaymentAmount = 0.0
        var familyLoanAmount = 0.0
        var otherLoanAmount = 0.0


//        if (nogodtakaText.isNullOrEmpty()) {
//        } else {
//            nogodtakaAmount = nogodtakaText.toString().toDouble()
//        }
//        if (nogodtakaBankText.isNullOrEmpty()) {
//        } else {
//            nogodtakaBankAmount = nogodtakaBankText.toString().toDouble()
//        }
//
//        if (!goldText.isNullOrEmpty()) {
//            goldAmount = goldText.toString().toDouble()
//        }
//
//        if (!silverText.isNullOrEmpty()) {
//            silverAmount = silverText.toString().toDouble()
//        }
//
//        if (!shareMarketText.isNullOrEmpty()) {
//            shareMarketAmount = shareMarketText.toString().toDouble()
//        }
//
//        if (!otherInvestmentText.isNullOrEmpty()) {
//            otherInvestmentAmount = otherInvestmentText.toString().toDouble()
//        }
//
//        if (!houseRentText.isNullOrEmpty()) {
//            houseRentAmount = houseRentText.toString().toDouble()
//        }
//
//        if (!assetText.isNullOrEmpty()) {
//            assetAmount = assetText.toString().toDouble()
//        }
//
//        if (!nogodbusinessText.isNullOrEmpty()) {
//            nogodbusinessAmount = nogodbusinessText.toString().toDouble()
//        }
//
//        if (!productText.isNullOrEmpty()) {
//            productAmount = productText.toString().toDouble()
//        }
//
//        if (!pensionText.isNullOrEmpty()) {
//            pensionAmount = pensionText.toString().toDouble()
//        }
//
//        if (!loanText.isNullOrEmpty()) {
//            loanAmount = loanText.toString().toDouble()
//        }
//
//        if (!otherCapitalText.isNullOrEmpty()) {
//            otherCapitalAmount = otherCapitalText.toString().toDouble()
//        }
//
//        if (!farmingText.isNullOrEmpty()) {
//            farmingAmount = farmingText.toString().toDouble()
//        }
//
//        if (!creditCardText.isNullOrEmpty()) {
//            creditCardAmount = creditCardText.toString().toDouble()
//        }
//
//        if (!carPaymentText.isNullOrEmpty()) {
//            carPaymentAmount = carPaymentText.toString().toDouble()
//        }
//
//        if (!businessPaymentText.isNullOrEmpty()) {
//            businessPaymentAmount = businessPaymentText.toString().toDouble()
//        }
//
//        if (!familyLoanText.isNullOrEmpty()) {
//            familyLoanAmount = familyLoanText.toString().toDouble()
//        }
//
//        if (!otherLoanText.isNullOrEmpty()) {
//            otherLoanAmount = otherLoanText.toString().toDouble()
//        }

        val totalAsset =
            nogodtakaAmount + nogodtakaBankAmount + goldAmount + silverAmount +
                    shareMarketAmount + otherInvestmentAmount + houseRentAmount +
                    assetAmount + nogodbusinessAmount + productAmount + pensionAmount +
                    loanAmount + otherCapitalAmount + farmingAmount

        val totalLiabilities = creditCardAmount +
                carPaymentAmount + businessPaymentAmount + familyLoanAmount + otherLoanAmount

        val totalNetAsset = totalAsset - totalLiabilities

        val totalZakat = ((totalNetAsset / 100) * 2.5)

//        binding.textTotalAsset.text = DecimalFormat("##.##").format(totalNetAsset)
//        binding.textTotalJakat.text = DecimalFormat("##.##").format(totalZakat)
    }
}
