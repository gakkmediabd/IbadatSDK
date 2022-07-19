package com.ibadat.sdk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ibadat.sdk.R
import com.ibadat.sdk.baseClass.BaseFragment
import com.ibadat.sdk.databinding.FragmentCalculateJakatBinding
import com.ibadat.sdk.util.handleClickEvent
import java.text.DecimalFormat


internal class CalculateJakatFragment : BaseFragment() {
    private lateinit var binding: FragmentCalculateJakatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calculate_jakat, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutNagadTakaHeader.tvTitleHeader.text =
            getText(R.string.title_nogod_taka)
        binding.layoutNagadTakacontent.contentTitle.text =
            getText(R.string.title_nogod_taka)
        binding.layoutBankNagadTakacontent.contentTitle.text =
            getText(R.string.title_bank_nogod_taka)
        binding.layoutOrnamentAmtHeader.tvTitleHeader.text =
            getText(R.string.title_ornament_header)
        binding.layoutGoldAmtcontent.contentTitle.text =
            getText(R.string.text_gold_amount)
        binding.layoutSilverAmtcontent.contentTitle.text =
            getText(R.string.text_silver_amount)
        binding.layoutInvestmentAmtHeader.tvTitleHeader.text =
            getText(R.string.title_investment)
        binding.layoutShareMarketcontent.contentTitle.text =
            getText(R.string.text_share_market)
        binding.layoutOtherInvestcontent.contentTitle.text =
            getText(R.string.text_other_investment)
        binding.layoutAssetHeader.tvTitleHeader.text = getText(R.string.title_asset)
        binding.layoutHouseRentcontent.contentTitle.text =
            getText(R.string.text_house_rent)
        binding.layoutAssetcontent.contentTitle.text = getText(R.string.title_asset)
        binding.layoutBusinessHeader.tvTitleHeader.text = getText(R.string.title_business)
        binding.layoutNogodBusinescontent.contentTitle.text =
            getText(R.string.text_nogod_business)
        binding.layoutProductcontent.contentTitle.text = getText(R.string.text_product)
        binding.layoutOtherHeader.tvTitleHeader.text = getText(R.string.title_other)
        binding.layoutPensioncontent.contentTitle.text = getText(R.string.text_pension)
        binding.layoutLoancontent.contentTitle.text = getText(R.string.text_family_loan)
        binding.layoutCapitalcontent.contentTitle.text =
            getText(R.string.text_other_capital)
        binding.layoutFarmingHeader.tvTitleHeader.text = getText(R.string.title_farming)
        binding.layoutFarmingcontent.contentTitle.text =
            getText(R.string.text_taka_amount)
        binding.layoutLiabilityHeader.tvTitleHeader.text =
            getText(R.string.title_lialibility)
        binding.layoutLiabilityHeader.tvTitleHeader.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.red
            )
        )

        binding.layoutCreditCardcontent.contentTitle.text =
            getText(R.string.text_credit_card)
        binding.layoutCreditCardcontent.tvSymbol.text

        binding.layoutCarcontent.contentTitle.text = getText(R.string.text_car_payment)
        binding.layoutBusinessPaymentcontent.contentTitle.text =
            getText(R.string.text_business_payment)
        binding.layoutFamilyLoancontent.contentTitle.text =
            getText(R.string.text_family_loan_liability)
        binding.layoutOtherLoancontent.contentTitle.text =
            requireContext().getText(R.string.text_other_loan)
        binding.btnSave.handleClickEvent {
            binding.constraint.visibility = View.VISIBLE
            val nogodtakaText = binding.layoutNagadTakacontent.etAmount.text
            val nogodtakaBankText = binding.layoutBankNagadTakacontent.etAmount.text
            val goldText = binding.layoutGoldAmtcontent.etAmount.text
            val silverText = binding.layoutSilverAmtcontent.etAmount.text
            val shareMarketText = binding.layoutShareMarketcontent.etAmount.text
            val otherInvestmentText = binding.layoutOtherInvestcontent.etAmount.text
            val houseRentText = binding.layoutHouseRentcontent.etAmount.text
            val assetText = binding.layoutAssetcontent.etAmount.text
            val nogodbusinessText = binding.layoutNogodBusinescontent.etAmount.text
            val productText = binding.layoutProductcontent.etAmount.text
            val pensionText = binding.layoutPensioncontent.etAmount.text
            val loanText = binding.layoutLoancontent.etAmount.text
            val otherCapitalText = binding.layoutOtherLoancontent.etAmount.text
            val farmingText = binding.layoutFarmingcontent.etAmount.text
            val creditCardText = binding.layoutCreditCardcontent.etAmount.text
            val carPaymentText = binding.layoutCarcontent.etAmount.text
            val businessPaymentText = binding.layoutBusinessPaymentcontent.etAmount.text
            val familyLoanText = binding.layoutFamilyLoancontent.etAmount.text
            val otherLoanText = binding.layoutOtherLoancontent.etAmount.text


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
            }

            val totalAsset =
                nogodtakaAmount + nogodtakaBankAmount + goldAmount + silverAmount +
                        shareMarketAmount + otherInvestmentAmount + houseRentAmount +
                        assetAmount + nogodbusinessAmount + productAmount + pensionAmount +
                        loanAmount + otherCapitalAmount + farmingAmount

            val totalLiabilities = creditCardAmount +
                    carPaymentAmount + businessPaymentAmount + familyLoanAmount + otherLoanAmount

            val totalNetAsset = totalAsset - totalLiabilities

            val totalZakat = ((totalNetAsset / 100) * 2.5)

            binding.textTotalAsset.text = DecimalFormat("##.##").format(totalNetAsset)
            binding.textTotalJakat.text = DecimalFormat("##.##").format(totalZakat)
        }
    }
}