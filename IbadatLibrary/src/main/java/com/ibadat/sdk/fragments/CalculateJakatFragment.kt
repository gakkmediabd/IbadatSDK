package com.ibadat.sdk.fragments

import android.os.Bundle
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
import com.ibadat.sdk.R
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.util.handleClickEvent
//import com.ibadat.sdk.databinding.FragmentCalculateJakatBinding
import com.ibadat.sdk.views.MyCustomTextView
import java.text.DecimalFormat


internal class CalculateJakatFragment : BaseFragment() {
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
    private lateinit var tvNagadTakaContentSymbol: TextView
    private lateinit var acetNagadTakaContentAmount: AppCompatEditText
    private lateinit var glNagadTakaContentGuideline3: Guideline

    //include_bank_nagad_taka_content
    private lateinit var ctvBankNagadTakaContentTitle: MyCustomTextView
    private lateinit var tvBankNagadTakaContentSymbol: TextView
    private lateinit var acetBankNagadTakaContentAmount: AppCompatEditText
    private lateinit var glBankNagadTakaContentGuideline3: Guideline

    //include_ornament_amt_header
    private lateinit var ctvOrnamentAmtHeaderTitleHeader: MyCustomTextView

    //include_gold_amt_content
    private lateinit var ctvGoldAmtContentContentTitle: MyCustomTextView
    private lateinit var tvGoldAmtContentSymbol: TextView
    private lateinit var acetGoldAmtContentAmount: AppCompatEditText
    private lateinit var glGoldAmtContentGuideline3: Guideline

    //include_silver_amt_content
    private lateinit var ctvSilverAmtContentContentTitle: MyCustomTextView
    private lateinit var tvSilverAmtContentSymbol: TextView
    private lateinit var acetSilverAmtContentAmount: AppCompatEditText
    private lateinit var glSilverAmtContentGuideline3: Guideline

    //include_investment_amt_header
    private lateinit var ctvInvestmentAmtHeaderTitleHeader: MyCustomTextView

    //include_share_market_content
    private lateinit var ctvShareMarketContentContentTitle: MyCustomTextView
    private lateinit var tvShareMarketContentSymbol: TextView
    private lateinit var acetShareMarketContentAmount: AppCompatEditText
    private lateinit var glShareMarketContentGuideline3: Guideline

    //include_other_invest_content
    private lateinit var ctvOtherInvestContentContentTitle: MyCustomTextView
    private lateinit var tvOtherInvestContentSymbol: TextView
    private lateinit var acetOtherInvestContentAmount: AppCompatEditText
    private lateinit var glOtherInvestContentGuideline3: Guideline

    //include_asset_header
    private lateinit var ctvAssetHeaderTitleHeader: MyCustomTextView

    //include_house_rent_content
    private lateinit var ctvHouseRentContentContentTitle: MyCustomTextView
    private lateinit var tvHouseRentContentSymbol: TextView
    private lateinit var acetHouseRentContentAmount: AppCompatEditText
    private lateinit var glHouseRentContentGuideline3: Guideline

    //include_asset_content
    private lateinit var ctvAssetContentContentTitle: MyCustomTextView
    private lateinit var tvAssetContentSymbol: TextView
    private lateinit var acetAssetContentAmount: AppCompatEditText
    private lateinit var glAssetContentGuideline3: Guideline


    //include_business_header
    private lateinit var ctvBusinessHeaderTitleHeader: MyCustomTextView

    //include_nogod_busines_content
    private lateinit var ctvNogodBusinesContentContentTitle: MyCustomTextView
    private lateinit var tvNogodBusinesContentSymbol: TextView
    private lateinit var acetNogodBusinesContentAmount: AppCompatEditText
    private lateinit var glNogodBusinesContentGuideline3: Guideline

    //include_product_content
    private lateinit var ctvProductContentContentTitle: MyCustomTextView
    private lateinit var tvProductContentSymbol: TextView
    private lateinit var acetProductContentAmount: AppCompatEditText
    private lateinit var glProductContentGuideline3: Guideline


    //include_other_header
    private lateinit var ctvOtherHeaderTitleHeader: MyCustomTextView

    //include_pension_content
    private lateinit var ctvPensionContentContentTitle: MyCustomTextView
    private lateinit var tvPensionContentSymbol: TextView
    private lateinit var acetPensionContentAmount: AppCompatEditText
    private lateinit var glPensionContentGuideline3: Guideline

    //include_loan_content
    private lateinit var ctvLoanContentContentTitle: MyCustomTextView
    private lateinit var tvLoanContentSymbol: TextView
    private lateinit var acetLoanContentAmount: AppCompatEditText
    private lateinit var glLoanContentGuideline3: Guideline

    //include_capital_content
    private lateinit var ctvCapitalContentTitle: MyCustomTextView
    private lateinit var tvCapitalContentSymbol: TextView
    private lateinit var acetCapitalContentAmount: AppCompatEditText
    private lateinit var glCapitalContentGuideline3: Guideline


    // include_farming_header
    private lateinit var ctvFarmingHeaderTitleHeader: MyCustomTextView

    //include_farming_content
    private lateinit var ctvFarmingContentContentTitle: MyCustomTextView
    private lateinit var tvFarmingContentSymbol: TextView
    private lateinit var acetFarmingContentAmount: AppCompatEditText
    private lateinit var glFarmingContentGuideline3: Guideline


    //include_liability_header
    private lateinit var ctvLiabilityHeaderTitleHeader: MyCustomTextView

    //include_credit_card_content
    private lateinit var ctvCreditCardContentContentTitle: MyCustomTextView
    private lateinit var tvCreditCardContentSymbol: TextView
    private lateinit var acetCreditCardContentAmount: AppCompatEditText
    private lateinit var glCreditCardContentGuideline3: Guideline

    //include_car_content
    private lateinit var ctvCarContentContentTitle: MyCustomTextView
    private lateinit var tvCarContentSymbol: TextView
    private lateinit var acetCarContentAmount: AppCompatEditText
    private lateinit var glCarContentGuideline3: Guideline

    //include_business_payment_content
    private lateinit var ctvBusinessPaymentContentContentTitle: MyCustomTextView
    private lateinit var tvBusinessPaymentContentSymbol: TextView
    private lateinit var acetBusinessPaymentContentAmount: AppCompatEditText
    private lateinit var glBusinessPaymentContentGuideline3: Guideline

    //include_family_loan_content
    private lateinit var ctvFamilyLoanContentContentTitle: MyCustomTextView
    private lateinit var tvFamilyLoanContentSymbol: TextView
    private lateinit var acetFamilyLoanContentAmount: AppCompatEditText
    private lateinit var glFamilyLoanContentGuideline3: Guideline

    //include_other_loan_content
    private lateinit var ctvOtherLoanContentContentTitle: MyCustomTextView
    private lateinit var tvOtherLoanContentSymbol: TextView
    private lateinit var acetOtherLoanContentAmount: AppCompatEditText
    private lateinit var glOtherLoanContentGuideline3: Guideline

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
        ctvNagadTakaHeaderTitleHeader = includeNagadTakaHeader.findViewById(R.id.ctv_title_header)

        val includeNagadTakaContent: ConstraintLayout =
            view.findViewById(R.id.include_nagad_taka_content)
        ctvNagadTakaContentTitle = includeNagadTakaContent.findViewById(R.id.ctv_content_title)
        tvNagadTakaContentSymbol = includeNagadTakaContent.findViewById(R.id.tv_symbol)
        acetNagadTakaContentAmount = includeNagadTakaContent.findViewById(R.id.acet_amount)
        glNagadTakaContentGuideline3 = includeNagadTakaContent.findViewById(R.id.gl_guideline3)
        val includeBankNagadTakaContent: ConstraintLayout =
            view.findViewById(R.id.include_bank_nagad_taka_content)
        ctvBankNagadTakaContentTitle =
            includeBankNagadTakaContent.findViewById(R.id.ctv_content_title)
        tvBankNagadTakaContentSymbol = includeBankNagadTakaContent.findViewById(R.id.tv_symbol)
        acetBankNagadTakaContentAmount = includeBankNagadTakaContent.findViewById(R.id.acet_amount)
        glBankNagadTakaContentGuideline3 =
            includeBankNagadTakaContent.findViewById(R.id.gl_guideline3)


        val includeOrnamentAmtHeader: ConstraintLayout =
            view.findViewById(R.id.include_ornament_amt_header)
        ctvOrnamentAmtHeaderTitleHeader =
            includeOrnamentAmtHeader.findViewById(R.id.ctv_title_header)

        val includeGoldAmtContent: ConstraintLayout =
            view.findViewById(R.id.include_gold_amt_content)
        ctvGoldAmtContentContentTitle =
            includeGoldAmtContent.findViewById(R.id.ctv_content_title)
        tvGoldAmtContentSymbol = includeGoldAmtContent.findViewById(R.id.tv_symbol)
        acetGoldAmtContentAmount = includeGoldAmtContent.findViewById(R.id.acet_amount)
        glGoldAmtContentGuideline3 = includeGoldAmtContent.findViewById(R.id.gl_guideline3)
        val includeSilverAmtContent: ConstraintLayout =
            view.findViewById(R.id.include_silver_amt_content)
        ctvSilverAmtContentContentTitle =
            includeSilverAmtContent.findViewById(R.id.ctv_content_title)
        tvSilverAmtContentSymbol = includeSilverAmtContent.findViewById(R.id.tv_symbol)
        acetSilverAmtContentAmount = includeSilverAmtContent.findViewById(R.id.acet_amount)
        glSilverAmtContentGuideline3 = includeSilverAmtContent.findViewById(R.id.gl_guideline3)


        val includeInvestmentAmtHeader: ConstraintLayout =
            view.findViewById(R.id.include_investment_amt_header)
        ctvInvestmentAmtHeaderTitleHeader =
            includeInvestmentAmtHeader.findViewById(R.id.ctv_title_header)

        val includeShareMarketContent: ConstraintLayout =
            view.findViewById(R.id.include_share_market_content)
        ctvShareMarketContentContentTitle =
            includeShareMarketContent.findViewById(R.id.ctv_content_title)
        tvShareMarketContentSymbol = includeShareMarketContent.findViewById(R.id.tv_symbol)
        acetShareMarketContentAmount = includeShareMarketContent.findViewById(R.id.acet_amount)
        glShareMarketContentGuideline3 =
            includeShareMarketContent.findViewById(R.id.gl_guideline3)
        val includeOtherInvestContent: ConstraintLayout =
            view.findViewById(R.id.include_other_invest_content)
        ctvOtherInvestContentContentTitle =
            includeOtherInvestContent.findViewById(R.id.ctv_content_title)
        tvOtherInvestContentSymbol = includeOtherInvestContent.findViewById(R.id.tv_symbol)
        acetOtherInvestContentAmount = includeOtherInvestContent.findViewById(R.id.acet_amount)
        glOtherInvestContentGuideline3 =
            includeOtherInvestContent.findViewById(R.id.gl_guideline3)


        val includeAssetHeader: ConstraintLayout =
            view.findViewById(R.id.include_asset_header)
        ctvAssetHeaderTitleHeader = includeAssetHeader.findViewById(R.id.ctv_title_header)

        val includeHouseRentContent: ConstraintLayout =
            view.findViewById(R.id.include_house_rent_content)
        ctvHouseRentContentContentTitle =
            includeHouseRentContent.findViewById(R.id.ctv_content_title)
        tvHouseRentContentSymbol = includeHouseRentContent.findViewById(R.id.tv_symbol)
        acetHouseRentContentAmount = includeHouseRentContent.findViewById(R.id.acet_amount)
        glHouseRentContentGuideline3 = includeHouseRentContent.findViewById(R.id.gl_guideline3)
        val includeAssetContent: ConstraintLayout =
            view.findViewById(R.id.include_asset_content)
        ctvAssetContentContentTitle = includeAssetContent.findViewById(R.id.ctv_content_title)
        tvAssetContentSymbol = includeAssetContent.findViewById(R.id.tv_symbol)
        acetAssetContentAmount = includeAssetContent.findViewById(R.id.acet_amount)
        glAssetContentGuideline3 = includeAssetContent.findViewById(R.id.gl_guideline3)


        val includeBusinessHeader: ConstraintLayout =
            view.findViewById(R.id.include_business_header)
        ctvBusinessHeaderTitleHeader = includeBusinessHeader.findViewById(R.id.ctv_title_header)

        val includeNogodBusinessContent: ConstraintLayout =
            view.findViewById(R.id.include_nogod_business_content)
        ctvNogodBusinesContentContentTitle =
            includeNogodBusinessContent.findViewById(R.id.ctv_content_title)
        tvNogodBusinesContentSymbol = includeNogodBusinessContent.findViewById(R.id.tv_symbol)
        acetNogodBusinesContentAmount =
            includeNogodBusinessContent.findViewById(R.id.acet_amount)
        glNogodBusinesContentGuideline3 =
            includeNogodBusinessContent.findViewById(R.id.gl_guideline3)
        val includeProductContent: ConstraintLayout =
            view.findViewById(R.id.include_product_content)
        ctvProductContentContentTitle =
            includeProductContent.findViewById(R.id.ctv_content_title)
        tvProductContentSymbol = includeProductContent.findViewById(R.id.tv_symbol)
        acetProductContentAmount = includeProductContent.findViewById(R.id.acet_amount)
        glProductContentGuideline3 = includeProductContent.findViewById(R.id.gl_guideline3)


        val includeOtherHeader: ConstraintLayout =
            view.findViewById(R.id.include_other_header)
        ctvOtherHeaderTitleHeader = includeOtherHeader.findViewById(R.id.ctv_title_header)

        val includePensionContent: ConstraintLayout =
            view.findViewById(R.id.include_pension_content)
        ctvPensionContentContentTitle =
            includePensionContent.findViewById(R.id.ctv_content_title)
        tvPensionContentSymbol = includePensionContent.findViewById(R.id.tv_symbol)
        acetPensionContentAmount = includePensionContent.findViewById(R.id.acet_amount)
        glPensionContentGuideline3 = includePensionContent.findViewById(R.id.gl_guideline3)
        val includeLoanContent: ConstraintLayout =
            view.findViewById(R.id.include_loan_content)
        ctvLoanContentContentTitle = includeLoanContent.findViewById(R.id.ctv_content_title)
        tvLoanContentSymbol = includeLoanContent.findViewById(R.id.tv_symbol)
        acetLoanContentAmount = includeLoanContent.findViewById(R.id.acet_amount)
        glLoanContentGuideline3 = includeLoanContent.findViewById(R.id.gl_guideline3)
        val includeCapitalContent: ConstraintLayout =
            view.findViewById(R.id.include_capital_content)
        ctvCapitalContentTitle = includeCapitalContent.findViewById(R.id.ctv_content_title)
        tvCapitalContentSymbol = includeCapitalContent.findViewById(R.id.tv_symbol)
        acetCapitalContentAmount = includeCapitalContent.findViewById(R.id.acet_amount)
        glCapitalContentGuideline3 = includeCapitalContent.findViewById(R.id.gl_guideline3)

        val includeFarmingHeader: ConstraintLayout =
            view.findViewById(R.id.include_farming_header)
        ctvFarmingHeaderTitleHeader = includeFarmingHeader.findViewById(R.id.ctv_title_header)
        val includeFarmingContent: ConstraintLayout =
            view.findViewById(R.id.include_farming_content)
        ctvFarmingContentContentTitle =
            includeFarmingContent.findViewById(R.id.ctv_content_title)
        tvFarmingContentSymbol = includeFarmingContent.findViewById(R.id.tv_symbol)
        acetFarmingContentAmount = includeFarmingContent.findViewById(R.id.acet_amount)
        glFarmingContentGuideline3 = includeFarmingContent.findViewById(R.id.gl_guideline3)


        val includeLiabilityHeader: ConstraintLayout =
            view.findViewById(R.id.include_liability_header)
        ctvLiabilityHeaderTitleHeader =
            includeLiabilityHeader.findViewById(R.id.ctv_title_header)
        val includeCreditCardContent: ConstraintLayout =
            view.findViewById(R.id.include_credit_card_content)
        ctvCreditCardContentContentTitle =
            includeCreditCardContent.findViewById(R.id.ctv_content_title)
        tvCreditCardContentSymbol = includeCreditCardContent.findViewById(R.id.tv_symbol)
        acetCreditCardContentAmount = includeCreditCardContent.findViewById(R.id.acet_amount)
        glCreditCardContentGuideline3 =
            includeCreditCardContent.findViewById(R.id.gl_guideline3)
        val includeCarContent: ConstraintLayout =
            view.findViewById(R.id.include_car_content)
        ctvCarContentContentTitle = includeCarContent.findViewById(R.id.ctv_content_title)
        tvCarContentSymbol = includeCarContent.findViewById(R.id.ctv_content_title)
        acetCarContentAmount = includeCarContent.findViewById(R.id.acet_amount)
        glCarContentGuideline3 = includeCarContent.findViewById(R.id.gl_guideline3)

        val includeBusinessPaymentContent: ConstraintLayout =
            view.findViewById(R.id.include_business_payment_content)
        ctvBusinessPaymentContentContentTitle =
            includeBusinessPaymentContent.findViewById(R.id.ctv_content_title)
        tvBusinessPaymentContentSymbol = includeBusinessPaymentContent.findViewById(R.id.tv_symbol)
        acetBusinessPaymentContentAmount =
            includeBusinessPaymentContent.findViewById(R.id.acet_amount)
        glBusinessPaymentContentGuideline3 =
            includeBusinessPaymentContent.findViewById(R.id.gl_guideline3)
        val includeFamilyLoanContent: ConstraintLayout =
            view.findViewById(R.id.include_family_loan_content)
        ctvFamilyLoanContentContentTitle =
            includeFamilyLoanContent.findViewById(R.id.ctv_content_title)
        tvFamilyLoanContentSymbol = includeFamilyLoanContent.findViewById(R.id.tv_symbol)
        acetFamilyLoanContentAmount = includeFamilyLoanContent.findViewById(R.id.acet_amount)
        glFamilyLoanContentGuideline3 =
            includeFamilyLoanContent.findViewById(R.id.gl_guideline3)
        val includeOtherLoanContent: ConstraintLayout =
            view.findViewById(R.id.include_other_loan_content)
        ctvOtherLoanContentContentTitle =
            includeOtherLoanContent.findViewById(R.id.ctv_content_title)
        tvOtherLoanContentSymbol = includeOtherLoanContent.findViewById(R.id.tv_symbol)
        acetOtherLoanContentAmount = includeOtherLoanContent.findViewById(R.id.acet_amount)
        glOtherLoanContentGuideline3 = includeOtherLoanContent.findViewById(R.id.gl_guideline3)

        btnSave = view.findViewById(R.id.btn_save)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ctvNagadTakaHeaderTitleHeader.text =
            getText(R.string.title_nogod_taka)
        ctvNagadTakaContentTitle.text =
            getText(R.string.title_nogod_taka)
        ctvBankNagadTakaContentTitle.text =
            getText(R.string.title_bank_nogod_taka)
        ctvOrnamentAmtHeaderTitleHeader.text =
            getText(R.string.title_ornament_header)
        ctvGoldAmtContentContentTitle.text =
            getText(R.string.text_gold_amount)
        ctvSilverAmtContentContentTitle.text =
            getText(R.string.text_silver_amount)
        ctvInvestmentAmtHeaderTitleHeader.text =
            getText(R.string.title_investment)
        ctvShareMarketContentContentTitle.text =
            getText(R.string.text_share_market)
        ctvOtherInvestContentContentTitle.text =
            getText(R.string.text_other_investment)
        ctvAssetHeaderTitleHeader.text = getText(R.string.title_asset)
        ctvHouseRentContentContentTitle.text =
            getText(R.string.text_house_rent)
        ctvAssetContentContentTitle.text = getText(R.string.title_asset)
        ctvBusinessHeaderTitleHeader.text = getText(R.string.title_business)
        ctvNogodBusinesContentContentTitle.text =
            getText(R.string.text_nogod_business)
        ctvProductContentContentTitle.text = getText(R.string.text_product)
        ctvOtherHeaderTitleHeader.text = getText(R.string.title_other)
        ctvPensionContentContentTitle.text = getText(R.string.text_pension)
        ctvLoanContentContentTitle.text = getText(R.string.text_family_loan)
        ctvCapitalContentTitle.text =
            getText(R.string.text_other_capital)
        ctvFarmingHeaderTitleHeader.text = getText(R.string.title_farming)
        ctvFarmingContentContentTitle.text =
            getText(R.string.text_taka_amount)
        ctvLiabilityHeaderTitleHeader.text =
            getText(R.string.title_lialibility)
        ctvLiabilityHeaderTitleHeader.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.red
            )
        )
        ctvCreditCardContentContentTitle.text =
            getText(R.string.text_credit_card)
//        binding.layoutCreditCardcontent.tvSymbol.text
        ctvCarContentContentTitle.text = getText(R.string.text_car_payment)
        ctvBusinessPaymentContentContentTitle.text =
            getText(R.string.text_business_payment)
        ctvFamilyLoanContentContentTitle.text =
            getText(R.string.text_family_loan_liability)
        ctvOtherLoanContentContentTitle.text =
            requireContext().getText(R.string.text_other_loan)
        btnSave.handleClickEvent {
            clCalculate.visibility = View.VISIBLE
            val nogodtakaText = acetNagadTakaContentAmount.text
            val nogodtakaBankText = acetBankNagadTakaContentAmount.text
            val goldText = acetGoldAmtContentAmount.text
            val silverText = acetSilverAmtContentAmount.text
            val shareMarketText = acetShareMarketContentAmount.text
            val otherInvestmentText = acetOtherInvestContentAmount.text
            val houseRentText = acetHouseRentContentAmount.text
            val assetText = acetAssetContentAmount.text
            val nogodbusinessText = acetNogodBusinesContentAmount.text
            val productText = acetProductContentAmount.text
            val pensionText = acetPensionContentAmount.text
            val loanText = acetLoanContentAmount.text
            val otherCapitalText = acetOtherLoanContentAmount.text
            val farmingText = acetFarmingContentAmount.text
            val creditCardText = acetCreditCardContentAmount.text
            val carPaymentText = acetCarContentAmount.text
            val businessPaymentText = acetBusinessPaymentContentAmount.text
            val familyLoanText = acetFamilyLoanContentAmount.text
            val otherLoanText = acetOtherLoanContentAmount.text

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

            if (nogodtakaText.isNullOrEmpty()) {
            } else {
                nogodtakaAmount = nogodtakaText.toString().toDouble()
            }

            if (nogodtakaBankText.isNullOrEmpty()) {
            } else {
                nogodtakaBankAmount = nogodtakaBankText.toString().toDouble()
            }

            if (!goldText.isNullOrEmpty()) {
                goldAmount = goldText.toString().toDouble()
            }

            if (!silverText.isNullOrEmpty()) {
                silverAmount = silverText.toString().toDouble()
            }

            if (!shareMarketText.isNullOrEmpty()) {
                shareMarketAmount = shareMarketText.toString().toDouble()
            }

            if (!otherInvestmentText.isNullOrEmpty()) {
                otherInvestmentAmount = otherInvestmentText.toString().toDouble()
            }

            if (!houseRentText.isNullOrEmpty()) {
                houseRentAmount = houseRentText.toString().toDouble()
            }

            if (!assetText.isNullOrEmpty()) {
                assetAmount = assetText.toString().toDouble()
            }

            if (!nogodbusinessText.isNullOrEmpty()) {
                nogodbusinessAmount = nogodbusinessText.toString().toDouble()
            }

            if (!productText.isNullOrEmpty()) {
                productAmount = productText.toString().toDouble()
            }

            if (!pensionText.isNullOrEmpty()) {
                pensionAmount = pensionText.toString().toDouble()
            }

            if (!loanText.isNullOrEmpty()) {
                loanAmount = loanText.toString().toDouble()
            }

            if (!otherCapitalText.isNullOrEmpty()) {
                otherCapitalAmount = otherCapitalText.toString().toDouble()
            }

            if (!farmingText.isNullOrEmpty()) {
                farmingAmount = farmingText.toString().toDouble()
            }

            if (!creditCardText.isNullOrEmpty()) {
                creditCardAmount = creditCardText.toString().toDouble()
            }

            if (!carPaymentText.isNullOrEmpty()) {
                carPaymentAmount = carPaymentText.toString().toDouble()
            }

            if (!businessPaymentText.isNullOrEmpty()) {
                businessPaymentAmount = businessPaymentText.toString().toDouble()
            }

            if (!familyLoanText.isNullOrEmpty()) {
                familyLoanAmount = familyLoanText.toString().toDouble()
            }

            if (!otherLoanText.isNullOrEmpty()) {
                otherLoanAmount = otherLoanText.toString().toDouble()

                val totalAsset =
                    nogodtakaAmount + nogodtakaBankAmount + goldAmount + silverAmount +
                            shareMarketAmount + otherInvestmentAmount + houseRentAmount +
                            assetAmount + nogodbusinessAmount + productAmount + pensionAmount +
                            loanAmount + otherCapitalAmount + farmingAmount

                val totalLiabilities = creditCardAmount +
                        carPaymentAmount + businessPaymentAmount + familyLoanAmount + otherLoanAmount

                val totalNetAsset = totalAsset - totalLiabilities

                val totalZakat = ((totalNetAsset / 100) * 2.5)

                ctvTotalAsset.text = DecimalFormat("##.##").format(totalNetAsset)
                ctvTotalJakat.text = DecimalFormat("##.##").format(totalZakat)
            }
        }
    }
}
