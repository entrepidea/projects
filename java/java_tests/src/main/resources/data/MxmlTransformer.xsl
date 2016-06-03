<?xml version="1.0"?>
<!--<xsl:text>deal_num;tran_group;tran_group_type; portfolio; commodity;leg_id;buy_sell;call_put;phys_fin;strike;quantity;first_pricing_date;last_pricing_date;payment_date;contract_month;premium_date;american_european;trade_start_date; trade_end_date;unit_premium;fix_flt;index1;index2;expiry;past_cash;future_cash;market_value;main_event;secondary_event</xsl:text>-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text"/>
	<xsl:template match="/">
		<xsl:text>@[EVENTS]</xsl:text>
		<xsl:call-template name="Events">
		</xsl:call-template>
		<xsl:call-template name="Trades">
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="Events">
		<xsl:text>;package=</xsl:text>
		<xsl:value-of select="count(//package)"></xsl:value-of>

		<xsl:text>;main_event=</xsl:text>
		<xsl:value-of select="//events/mainEvent/action"></xsl:value-of>

		<xsl:text>;main_event_nature=</xsl:text>
		<xsl:value-of select="//events/mainEvent/object/objectNature"></xsl:value-of>

		<xsl:text>;secondary_event1=</xsl:text>
		<xsl:value-of select="//events/secondaryEvent[1]/action"></xsl:value-of>

		<xsl:text>;secondary_event1_nature=</xsl:text>
		<xsl:value-of select="//events/secondaryEvent[1]/object/objectNature"></xsl:value-of>

		<xsl:text>;secondary_event2=</xsl:text>
		<xsl:value-of select="//events/secondaryEvent[2]/action"></xsl:value-of>

		<xsl:text>;secondary_event2_nature=</xsl:text>
		<xsl:value-of select="//events/secondaryEvent[2]/object/objectNature"></xsl:value-of>
	</xsl:template>


	<xsl:template name="Trades">
		<xsl:param name="tradeExtracted"></xsl:param>
		<!--<xsl:variable name="tradeExtracted" select="$tradeExtracted"></xsl:variable>-->
		<xsl:variable name="packageCount" select="count(//packages)"></xsl:variable>
		<!--<xsl:text>&#xA;</xsl:text>-->
		<xsl:text>&#xA;</xsl:text>
		<xsl:for-each select="//trade">
			<xsl:element name="legs">
				<xsl:variable name="trPosition" select="position()"></xsl:variable>

				<xsl:text>@[TRADE]</xsl:text>

				<xsl:text>system_date=</xsl:text>
				<xsl:value-of select="/MxML/documentProperties[1]/systemDate[1]"></xsl:value-of>

				<xsl:text>;deal_num=</xsl:text>
				<xsl:value-of select="tradeHeader/tradeViews/tradeView[1]/tradeId/tradeInternalId"></xsl:value-of>

				<xsl:text>;tran_group=</xsl:text>
				<xsl:value-of select="./*/tradeCategory/tradeGroup"></xsl:value-of>
				<xsl:variable name="tran_group" select="normalize-space(./*/tradeCategory/tradeGroup)"></xsl:variable>

				<xsl:text>;tran_group_type=</xsl:text>
				<xsl:value-of select="./*/tradeCategory/tradeType"></xsl:value-of>

				<xsl:text>;portfolio1=</xsl:text>
				<xsl:value-of select="portfolios/portfolio[1]/portfolioLabel"></xsl:value-of>
				<xsl:variable name="portfolio1" select="normalize-space(portfolios/portfolio[1]/portfolioLabel)"></xsl:variable>

				<xsl:text>;portfolio2=</xsl:text>
				<xsl:value-of select="portfolios/portfolio[2]/portfolioLabel"></xsl:value-of>
				<xsl:variable name="portfolio2" select="normalize-space(portfolios/portfolio[2]/portfolioLabel)"></xsl:variable>

				<xsl:text>;commodity=</xsl:text>
				<xsl:value-of select=".//deliveryData/physicalProductLabel"></xsl:value-of>

				<xsl:text>;leg_id=</xsl:text>
				<xsl:text>0</xsl:text>

				<xsl:text>;destination=</xsl:text>
				<xsl:value-of select=".//tradeDestination"></xsl:value-of>
				<xsl:variable name="destination" select="normalize-space(.//tradeDestination)"></xsl:variable>

				<xsl:text>;counter_party=</xsl:text>
				<xsl:choose>
					<xsl:when test="$destination='external'">
						<xsl:value-of select="parties/party[2]/partyName"></xsl:value-of>
					</xsl:when>
					<xsl:otherwise>
						<!-- for internal deals the logiv is in MiscTransformer-->
					</xsl:otherwise>
				</xsl:choose>

				<xsl:text>;trader=</xsl:text>
				<xsl:value-of select="tradeHeader/tradeViews/tradeView[1]/userName"></xsl:value-of>
				<xsl:text>;trader2=</xsl:text>
				<xsl:value-of select="tradeHeader/tradeViews/tradeView[2]/userName"></xsl:value-of>
				<!--<xsl:variable name="trader" select="normalize-space(tradeHeader/tradeViews/tradeView[2]/userName)"></xsl:variable>
				<xsl:choose>
					<xsl:when test="($trader = '')">
						<xsl:value-of select="tradeHeader/tradeViews/tradeView[1]/userName"></xsl:value-of>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="($trader)"></xsl:value-of>
					</xsl:otherwise>
				</xsl:choose>-->


				<!--			<xsl:text>;currency=</xsl:text>
				<xsl:value-of select="tradeBody/*/stream/streamTemplate/paymentCurrency"></xsl:value-of>-->



				<!-- PHYS/FIN -->
				<xsl:text>;phys_fin=FIN</xsl:text>
				<!--<xsl:variable name="fin_phy" select="tradeBody/*/stream/streamTemplate/settlementNature"></xsl:variable>
				<xsl:choose>
					<xsl:when test="$fin_phy='cashSettled'">
						<xsl:text>FIN</xsl:text>
					</xsl:when>
					<xsl:otherwise>
					</xsl:otherwise>
				</xsl:choose>-->



				<xsl:text>;premium_date=</xsl:text>
				<xsl:value-of select="tradeBody/*/settlement/settlementFlow/flow/date"></xsl:value-of>

				<xsl:variable name="family" select="./*/tradeCategory/tradeFamily"></xsl:variable>
				<xsl:variable name="group" select="./*/tradeCategory/tradeGroup"></xsl:variable>
				<xsl:text>;family=</xsl:text>
				<xsl:value-of select="($family)"></xsl:value-of>
				<xsl:text>;group=</xsl:text>
				<xsl:value-of select="($group)"></xsl:value-of>


				<xsl:text>;currency=</xsl:text>
				<xsl:choose>
					<xsl:when test="($group='FUT')">
						<xsl:value-of select="tradeBody/commodityFuture/commodityFutureId/futureQuotation/quotationCurrency"></xsl:value-of>
					</xsl:when>
					<xsl:when test="($group='OFUT')">
						<xsl:value-of select="tradeBody/commodityFutureOption/commodityFutureId/futureQuotation/quotationCurrency"></xsl:value-of>
					</xsl:when>
					<xsl:when test="($group='ASIAN') or ($group='OPT') or ($group='SWAP')">
						<xsl:value-of select="tradeBody/*/stream/streamTemplate/paymentCurrency"></xsl:value-of>
					</xsl:when>
				</xsl:choose>


				<xsl:variable name="strike_var1" select="normalize-space(tradeBody/*/strike)"></xsl:variable>
				<xsl:variable name="strike_var2" select="normalize-space(tradeBody/*/stream/floatingRateStream/streamStrikes/strike)"></xsl:variable>

				<xsl:choose>
					<xsl:when test="($group='FUT') or ($group='OFUT') or ($group='ASIAN')">
						<xsl:text>;strike=</xsl:text>
						<xsl:choose>
							<xsl:when test="($strike_var1 != '')">
								<xsl:value-of select="($strike_var1)"></xsl:value-of>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="($strike_var2)"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
				</xsl:choose>

				<!-- start call_put -->
				<xsl:choose>
					<xsl:when test="($group!='OPT')">
						<xsl:variable name="call_put_var1" select="normalize-space(tradeBody/*/callPut)"></xsl:variable>
						<xsl:text>;call_put_var1=</xsl:text>
						<xsl:value-of select="($call_put_var1)"></xsl:value-of>
						<xsl:choose>
							<xsl:when test="($call_put_var1='')">
								<xsl:variable name="call_put_var2" select="normalize-space(tradeBody/*/stream/floatingRateStream/streamPayout)"></xsl:variable>
								<xsl:text>;call_put_var2=</xsl:text>
								<xsl:value-of select="($call_put_var2)"></xsl:value-of>
								<xsl:choose>
									<xsl:when test="($call_put_var2='')">
										<xsl:text>;call_put=</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>;call_put=</xsl:text>
										<xsl:call-template name="first-upper-only">
											<xsl:with-param name="value" select="($call_put_var2)"></xsl:with-param>
										</xsl:call-template>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>;call_put=</xsl:text>
								<xsl:call-template name="first-upper-only">
									<xsl:with-param name="value" select="($call_put_var1)"></xsl:with-param>
								</xsl:call-template>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>;call_put=</xsl:text>
						<xsl:choose>
							<xsl:when test="$destination='external'">
								<xsl:variable name="optionHolder" select="substring-after(tradeBody/*/payout/option/optionHolderReference/@*,'#')"></xsl:variable>
								<xsl:variable name="payerParty" select="substring(tradeBody/*/stream[1]/payerPartyReference/@href,2)"></xsl:variable>
								<xsl:choose>
									<xsl:when test="$optionHolder=$payerParty">
										<xsl:text>P</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>C</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<!-- for internal opt -->
								<xsl:variable name="optionHolder_int" select="substring-after(tradeBody/*/payout/option/optionHolderReference/portfolioReference/@*,'#')"></xsl:variable>
								<xsl:variable name="payerParty_int" select="substring(tradeBody/*/stream[1]/payerPartyReference/portfolioReference/@href,2)"></xsl:variable>
								<xsl:choose>
									<xsl:when test="$optionHolder_int=$payerParty_int">
										<xsl:text>P</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>C</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>

				<!-- end call_put -->

				<!-- start american_european -->

				<xsl:text>;american_european=</xsl:text>
				<xsl:choose>
					<xsl:when test="($group='OFUT')">
						<xsl:call-template name="first-upper-only">
							<xsl:with-param name="value" select="normalize-space(tradeBody/*/option/optionStyle)"></xsl:with-param>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="($group='OPT')">
						<xsl:call-template name="first-upper-only">
							<xsl:with-param name="value" select="normalize-space(tradeBody/*/payout/option/optionStyle)"></xsl:with-param>
						</xsl:call-template>
					</xsl:when>
					<xsl:when test="($group='ASIAN')">
						<xsl:text>E</xsl:text>
					</xsl:when>
				</xsl:choose>

				<xsl:choose>
					<xsl:when test="($group='ASIAN') or ($group='OPT')">
						<xsl:text>;unit_premium=</xsl:text>
						<xsl:variable name="unit_premium_var1" select="normalize-space(tradeBody/*/settlement/settlementFlow/price/priceRate)"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($unit_premium_var1 != '')">
								<xsl:value-of select="($unit_premium_var1)"></xsl:value-of>
							</xsl:when>
							<xsl:otherwise>
								<xsl:choose>
									<xsl:when test="($strike_var1 != '')">
										<xsl:value-of select="($strike_var1)"></xsl:value-of>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="($strike_var2)"></xsl:value-of>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:when test="($group='OFUT')">
						<xsl:text>;unit_premium=</xsl:text>
						<xsl:variable name="unit_premium_var1" select="normalize-space(tradeBody/*/settlement/settlementFlow/flow/amount)"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($unit_premium_var1 != '')">
								<xsl:value-of select="($unit_premium_var1)"></xsl:value-of>
							</xsl:when>
							<xsl:otherwise>
								<xsl:choose>
									<xsl:when test="($strike_var1 != '')">
										<xsl:value-of select="($strike_var1)"></xsl:value-of>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="($strike_var2)"></xsl:value-of>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
				</xsl:choose>


				<xsl:choose>
					<xsl:when test="($group='SWAP')">
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>;fix_flt=</xsl:text>
						<xsl:text>FIX</xsl:text>
					</xsl:otherwise>
				</xsl:choose>


				<!-- index1 -->
				<xsl:choose>
					<xsl:when test="($group='FUT') or ($group='OFUT')">
						<xsl:variable name="index1" select="normalize-space(tradeBody/*/commodityFutureId/futureLabel)"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($index1='')">
								<xsl:choose>
									<xsl:when test="($group='FUT')">
										<xsl:text>;index1=</xsl:text>
										<xsl:value-of select="tradeBody/commodityFuture/commodityFutureId/futureLabel"></xsl:value-of>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>;index1=</xsl:text>
										<xsl:value-of select="tradeBody/commodityFutureOption/commodityFutureId/futureLabel"></xsl:value-of>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>;index1=</xsl:text>
								<xsl:value-of select="($index1)"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="underlyingComIndex" select="normalize-space(tradeBody/*/stream[1]/streamTemplate/floatingRateStreamTemplate/index/indexFormula/averagedIndex/averageUnderlyingIndex/commodityIndex/indexLabel)"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($underlyingComIndex='')">
								<xsl:text>;index1=</xsl:text>
								<xsl:variable name="index1_var" select="normalize-space(tradeBody/*/stream[1]/streamTemplate/floatingRateStreamTemplate/index/indexFormula/averagedIndex/averageUnderlyingIndex/indexLabel)"></xsl:variable>
								<xsl:choose>
									<xsl:when test="($index1_var='')">
										<xsl:variable name="idxFormula" select="normalize-space(tradeBody/*/stream/streamTemplate/floatingRateStreamTemplate/index/indexFormula/indexFormulaType)"></xsl:variable>
										<!--<xsl:choose>
											<xsl:when test="($idxFormula='basket')">-->
										<xsl:value-of select="tradeBody/*/stream/streamTemplate/floatingRateStreamTemplate/index/indexLabel"></xsl:value-of>
										<!--</xsl:when>
											<xsl:otherwise>
											</xsl:otherwise>
										</xsl:choose>-->
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="($index1_var)"></xsl:value-of>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>;index1=</xsl:text>
								<xsl:value-of select="($underlyingComIndex)"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>
				<!-- end of index1-->


				<xsl:text>;index2=</xsl:text>
				<xsl:choose>
					<xsl:when test="($group='FUT') or ($group='OFUT')">
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="underlyingComIndex" select="normalize-space(tradeBody/*/stream[2]/streamTemplate/floatingRateStreamTemplate/index/indexFormula/averagedIndex/averageUnderlyingIndex/commodityIndex/indexLabel)"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($underlyingComIndex='')">
								<xsl:value-of select="tradeBody/*/stream[2]/streamTemplate/floatingRateStreamTemplate/index/indexFormula/averagedIndex/averageUnderlyingIndex/indexLabel"></xsl:value-of>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="$underlyingComIndex"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>


				<xsl:text>;expiry=</xsl:text>
				<xsl:choose>
					<xsl:when test="($group='FUT') or ($group='OFUT')">
						<xsl:value-of select="normalize-space(tradeBody/*/option/optionMaturity/date)"></xsl:value-of>
					</xsl:when>
					<xsl:when test="($group='OPT')">
						<xsl:value-of select="normalize-space(tradeBody/*/payout/option/optionMaturity/date)"></xsl:value-of>
					</xsl:when>
				</xsl:choose>


				<!-- TO DO Need to add past cash , future cash , market value -->
			<!--	<xsl:text>;past_cash=0</xsl:text>
				<xsl:text>;future_cash=0</xsl:text>-->
				<xsl:text>;market_value=</xsl:text>


				<xsl:text>;murex_trade_status=</xsl:text>
				<xsl:value-of select="tradeStatus/physicalStatus"></xsl:value-of>

				<xsl:variable name="typology" select="tradeBody/tradeUserDefinedType"></xsl:variable>
				<xsl:text>;typology=</xsl:text>
				<xsl:call-template name="upper-case">
					<xsl:with-param name="value" select="($typology)"></xsl:with-param>
				</xsl:call-template>


				<xsl:text>;link_ref=</xsl:text>
				<xsl:value-of select="tradeHeader/tradeLink/linkId"></xsl:value-of>

				<xsl:text>;trade_date=</xsl:text>
				<xsl:value-of select="./*/tradeDate"></xsl:value-of>

				<!-- BUY/SELL -->
				<xsl:variable name="portfolio_id" select="portfolios/portfolio[1]/@*"></xsl:variable>
				<xsl:choose>
					<xsl:when test="($tran_group='OPT')">
						<xsl:choose>
							<xsl:when test="$destination='external'">
								<xsl:variable name="optionHolder" select="substring-after(tradeBody/*/payout/option/optionHolderReference/@*,'#')"></xsl:variable>
								<xsl:variable name="optionWriter" select="substring-after(tradeBody/*/payout/option/optionWriterReference/@*,'#')"></xsl:variable>
								<xsl:text>;optionHolder=</xsl:text>
								<xsl:value-of select="$optionHolder"></xsl:value-of>
								<xsl:text>;optionWriter=</xsl:text>
								<xsl:value-of select="$optionWriter"></xsl:value-of>
								<xsl:choose>
									<xsl:when test="$optionWriter='BOFA_COM'">
										<xsl:text>;buy_sell=S</xsl:text>
									</xsl:when>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="$optionHolder='BOFA_COM'">
										<xsl:text>;buy_sell=B</xsl:text>
									</xsl:when>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:variable name="optionHolder" select="normalize-space(substring-after(tradeBody/*/payout/option/optionHolderReference/portfolioReference/@*,'#'))"></xsl:variable>
								<xsl:variable name="optionWriter" select="normalize-space(substring-after(tradeBody/*/payout/option/optionWriterReference/portfolioReference/@*,'#'))"></xsl:variable>
								<xsl:text>;optionHolder=</xsl:text>
								<xsl:value-of select="$optionHolder"></xsl:value-of>
								<xsl:text>;optionWriter=</xsl:text>
								<xsl:value-of select="$optionWriter"></xsl:value-of>
								<xsl:choose>
									<xsl:when test="$optionWriter=$portfolio_id">
										<xsl:text>;buy_sell=S</xsl:text>
									</xsl:when>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="$optionHolder=$portfolio_id">
										<xsl:text>;buy_sell=B</xsl:text>
									</xsl:when>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>

					<!--<xsl:when test="($typology='ASIAN_OPTION') or ($typology='COLLAR') or ($typology='STRADDLE') or ($typology='STRANGLE') or ($typology='BULLET_OPTION')">-->
					<xsl:when test="($group='ASIAN')">
						<xsl:choose>
							<xsl:when test="$destination='external'">
								<xsl:variable name="premiumPayer" select="substring-after(tradeBody/*/settlement/settlementFlow/flow/payerPartyReference/@*,'#')"></xsl:variable>
								<xsl:variable name="premiumReceiver" select="substring-after(tradeBody/*/settlement/settlementFlow/flow/receiverPartyReference/@*,'#')"></xsl:variable>
								<xsl:text>;premiumPayer=</xsl:text>
								<xsl:value-of select="$premiumPayer"></xsl:value-of>
								<xsl:text>;premiumReceiver=</xsl:text>
								<xsl:value-of select="$premiumReceiver"></xsl:value-of>
								<xsl:choose>
									<xsl:when test="$premiumReceiver='BOFA_COM'">;buy_sell=S</xsl:when>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="$premiumPayer='BOFA_COM'">;buy_sell=B</xsl:when>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:variable name="premiumPayer" select="substring-after(tradeBody/*/settlement/settlementFlow/flow/payerPartyReference/portfolioReference/@*,'#')"></xsl:variable>
								<xsl:variable name="premiumReceiver" select="substring-after(tradeBody/*/settlement/settlementFlow/flow/receiverPartyReference/portfolioReference/@*,'#')"></xsl:variable>
								<xsl:text>;premiumPayer=</xsl:text>
								<xsl:value-of select="$premiumPayer"></xsl:value-of>
								<xsl:text>;premiumReceiver=</xsl:text>
								<xsl:value-of select="$premiumReceiver"></xsl:value-of>
								<xsl:choose>
									<xsl:when test="$premiumReceiver=$portfolio_id">;buy_sell=S</xsl:when>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="$premiumPayer=$portfolio_id">;buy_sell=B</xsl:when>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>

					<xsl:when test="($group='FUT')">
						<xsl:choose>
							<xsl:when test="$destination='external'">
								<xsl:variable name="ourself" select="translate(parties/party[1]/partyName,' ','_')"></xsl:variable>
								<xsl:variable name="holder" select="substring(tradeBody/*/futureHolderReference/@href,2)"></xsl:variable>
								<xsl:text>;ourself=</xsl:text>
								<xsl:value-of select="$ourself"></xsl:value-of>
								<xsl:text>;holder=</xsl:text>
								<xsl:value-of select="$holder"></xsl:value-of>
								<xsl:choose>
									<xsl:when test="$holder=($ourself)">;buy_sell=B</xsl:when>
									<xsl:otherwise>;buy_sell=S</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:variable name="ourself" select="translate(parties/party[1]/partyName,' ','_')"></xsl:variable>
								<xsl:variable name="holder" select="substring(tradeBody/*/futureHolderReference/portfolioReference/@href,2)"></xsl:variable>
								<xsl:text>;ourself=</xsl:text>
								<xsl:value-of select="$ourself"></xsl:value-of>
								<xsl:text>;holder=</xsl:text>
								<xsl:value-of select="$holder"></xsl:value-of>
								<xsl:choose>
									<xsl:when test="$holder=($portfolio_id)">;buy_sell=B</xsl:when>
									<xsl:otherwise>;buy_sell=S</xsl:otherwise>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>


					<xsl:when test="($group='SWAP')">
						<xsl:choose>
							<xsl:when test="$destination='external'">
								<xsl:variable name="ourself" select="translate(parties/party[1]/partyName,' ','_')"></xsl:variable>
								<xsl:for-each select="tradeBody/*/stream">
									<xsl:variable name="phase" select="./streamTemplate/phase"></xsl:variable>
									<xsl:variable name="leg" select="./streamTemplate/leg"></xsl:variable>
									<xsl:if test="$leg=1">
										<xsl:variable name="payer" select="substring(./payerPartyReference/@href,2)"></xsl:variable>
										<xsl:text>;ourself=</xsl:text>
										<xsl:value-of select="$ourself"></xsl:value-of>
										<xsl:text>;payer=</xsl:text>
										<xsl:value-of select="$payer"></xsl:value-of>
										<xsl:choose>
											<xsl:when test="$payer=$ourself">;buy_sell=S</xsl:when>
											<xsl:otherwise>;buy_sell=B</xsl:otherwise>
										</xsl:choose>
									</xsl:if>
								</xsl:for-each>
							</xsl:when>
							<xsl:otherwise>
								<xsl:variable name="ourself" select="translate(parties/party[1]/partyName,' ','_')"></xsl:variable>
								<xsl:for-each select="tradeBody/*/stream">
									<xsl:variable name="phase" select="./streamTemplate/phase"></xsl:variable>
									<xsl:variable name="leg" select="./streamTemplate/leg"></xsl:variable>
									<xsl:if test="$leg=1">
										<xsl:variable name="payer" select="substring(./payerPartyReference/portfolioReference/@href,2)"></xsl:variable>
										<xsl:text>;ourself=</xsl:text>
										<xsl:value-of select="$ourself"></xsl:value-of>
										<xsl:text>;payer=</xsl:text>
										<xsl:value-of select="$payer"></xsl:value-of>
										<xsl:choose>
											<xsl:when test="$payer=$portfolio_id">;buy_sell=S</xsl:when>
											<xsl:otherwise>;buy_sell=B</xsl:otherwise>
										</xsl:choose>
									</xsl:if>
								</xsl:for-each>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>

					<xsl:when test="($tran_group='OFUT')">
						<xsl:choose>
							<xsl:when test="$destination='external'">
								<xsl:variable name="optionHolder" select="substring-after(tradeBody/commodityFutureOption/option/optionHolderReference/@*,'#')"></xsl:variable>
								<xsl:variable name="optionWriter" select="substring-after(tradeBody/commodityFutureOption/option/optionWriterReference/@*,'#')"></xsl:variable>
								<xsl:text>;optionHolder=</xsl:text>
								<xsl:value-of select="$optionHolder"></xsl:value-of>
								<xsl:text>;optionWriter=</xsl:text>
								<xsl:value-of select="$optionWriter"></xsl:value-of>
								<xsl:choose>
									<xsl:when test="$optionWriter='BOFA_COM'">
										<xsl:text>;buy_sell=S</xsl:text>
									</xsl:when>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="$optionHolder='BOFA_COM'">
										<xsl:text>;buy_sell=B</xsl:text>
									</xsl:when>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:variable name="optionHolder" select="substring-after(tradeBody/commodityFutureOption/option/optionHolderReference/portfolioReference/@*,'#')"></xsl:variable>
								<xsl:variable name="optionWriter" select="substring-after(tradeBody/commodityFutureOption/option/optionWriterReference/portfolioReference/@*,'#')"></xsl:variable>
								<xsl:text>;optionHolder=</xsl:text>
								<xsl:value-of select="$optionHolder"></xsl:value-of>
								<xsl:text>;optionWriter=</xsl:text>
								<xsl:value-of select="$optionWriter"></xsl:value-of>
								<xsl:choose>
									<xsl:when test="$optionWriter=$portfolio_id">
										<xsl:text>;buy_sell=S</xsl:text>
									</xsl:when>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="$optionHolder=$portfolio_id">
										<xsl:text>;buy_sell=B</xsl:text>
									</xsl:when>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>

					<xsl:otherwise>
					</xsl:otherwise>
				</xsl:choose>

				<!-- end of BUY/SELL -->

				<!-- trade_start_date-->
				<xsl:choose>
					<xsl:when test="($group='FUT') or ($group='OFUT')">
						<xsl:variable name="trade_start_date" select="normalize-space(tradeBody/*/commodityFutureId/futureMaturity/expiryDate)"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($trade_start_date='')">
								<xsl:choose>
									<xsl:when test="($group='FUT')">
										<xsl:text>;trade_start_date=</xsl:text>
										<xsl:value-of select="tradeBody/commodityFuture/commodityFutureId/futureMaturity/expiryDate"></xsl:value-of>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>;trade_start_date=</xsl:text>
										<xsl:value-of select="tradeBody/commodityFutureOption/commodityFutureId/futureMaturity/expiryDate"></xsl:value-of>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>;trade_start_date=</xsl:text>
								<xsl:value-of select="($trade_start_date)"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:when test="($group='SWAP') or ($group='ASIAN')or ($group='OPT')">
						<xsl:text>;trade_start_date=</xsl:text>
						<xsl:value-of select="tradeBody/*/stream/effectiveDate"></xsl:value-of>
					</xsl:when>
				</xsl:choose>
				<!-- end of trade_start_date-->


				<!-- trade_end_start_date-->
				<xsl:choose>
					<xsl:when test="($group='FUT') or ($group='OFUT')">
						<xsl:variable name="trade_end_date" select="normalize-space(tradeBody/*/commodityFutureId/futureMaturity/expiryDate)"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($trade_end_date='')">
								<xsl:choose>
									<xsl:when test="($group='FUT')">
										<xsl:text>;trade_end_date=</xsl:text>
										<xsl:value-of select="tradeBody/commodityFuture/commodityFutureId/futureMaturity/expiryDate"></xsl:value-of>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>;trade_end_date=</xsl:text>
										<xsl:value-of select="tradeBody/commodityFutureOption/commodityFutureId/futureMaturity/expiryDate"></xsl:value-of>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>;trade_end_date=</xsl:text>
								<xsl:value-of select="($trade_end_date)"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:when test="($group='SWAP') or ($group='ASIAN') or ($group='OPT')">
						<xsl:text>;trade_end_date=</xsl:text>
						<xsl:value-of select="tradeBody/*/stream/maturity"></xsl:value-of>
					</xsl:when>
				</xsl:choose>
				<!-- end of trade_start_date-->


				<xsl:if test="($group='FUT') or ($group='OFUT')">
					<xsl:variable name="quantity" select="normalize-space(tradeBody/*/commodityDelivery/productFlowDetails/productFlow/deliveredQuantity/quantity)"></xsl:variable>
					<xsl:choose>
						<xsl:when test="($quantity='')">
							<xsl:choose>
								<xsl:when test="($group='FUT')">
									<xsl:text>;quantity=</xsl:text>
									<xsl:value-of select="tradeBody/commodityFuture/commodityDelivery/productFlowDetails/productFlow/deliveredQuantity/quantity"></xsl:value-of>
								</xsl:when>
								<xsl:otherwise>
									<xsl:text>;quantity=</xsl:text>
									<xsl:value-of select="tradeBody/commodityFutureOption/commodityDelivery/productFlowDetails/productFlow/deliveredQuantity/quantity"></xsl:value-of>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>;quantity=</xsl:text>
							<xsl:value-of select="($quantity)"></xsl:value-of>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:if>


				<xsl:if test="($group='FUT')">
					<xsl:text>;first_pricing_date=</xsl:text>
					<xsl:value-of select="tradeBody/commodityFuture/commodityFutureId/futureMaturity/expiryDate"></xsl:value-of>

					<xsl:text>;last_pricing_date=</xsl:text>
					<xsl:value-of select="tradeBody/commodityFuture/commodityFutureId/futureMaturity/expiryDate"></xsl:value-of>

					<xsl:text>;payment_date=</xsl:text>
					<xsl:value-of select="tradeBody/commodityFuture/commodityFutureId/futureMaturity/expiryDate"></xsl:value-of>
				</xsl:if>

				<xsl:if test="($group='OFUT')">
					<xsl:text>;first_pricing_date=</xsl:text>
					<xsl:value-of select="tradeBody/commodityFutureOption/option/optionMaturity/date"></xsl:value-of>

					<xsl:text>;last_pricing_date=</xsl:text>
					<xsl:value-of select="tradeBody/commodityFutureOption/option/optionMaturity/date"></xsl:value-of>

					<xsl:text>;payment_date=</xsl:text>
					<xsl:value-of select="tradeBody/commodityFutureOption/option/optionMaturity/date"></xsl:value-of>
				</xsl:if>

				<!-- contract_month -->
				<xsl:choose>
					<xsl:when test="($group='FUT') or ($group='OFUT')">
						<xsl:variable name="contract_month_var" select="normalize-space(tradeBody/*/commodityFutureId/futureMaturity/maturityLabel)"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($contract_month_var='')">
								<xsl:choose>
									<xsl:when test="($group='FUT')">
										<xsl:text>;contract_month=</xsl:text>
										<xsl:value-of select="tradeBody/commodityFuture/commodityFutureId/futureMaturity/maturityLabel"></xsl:value-of>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>;contract_month=</xsl:text>
										<xsl:value-of select="tradeBody/commodityFutureOption/commodityFutureId/futureMaturity/maturityLabel"></xsl:value-of>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>;contract_month=</xsl:text>
								<xsl:value-of select="($contract_month_var)"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>

					<xsl:when test="($group='ASIAN') or ($group='OPT')">
						<xsl:text>;contract_month=</xsl:text>
						<xsl:value-of select="tradeBody/*/settlement/settlementFlow/flow/date"></xsl:value-of>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>;contract_month=</xsl:text>
						<xsl:value-of select="tradeBody/*/stream/maturity"></xsl:value-of>
					</xsl:otherwise>
				</xsl:choose>
				<!-- end contract_month -->

				<xsl:text>;qty_var_for_exercize=</xsl:text>
				<xsl:value-of select="((//operationSources/operationSource/operationLiveQuantity) - (//operationSources/operationSource/operationQuantity)) * tradeBody/*/commodityDelivery/lotSize"></xsl:value-of>

				<xsl:choose>
					<xsl:when test="($group='FUT') or ($group='OFUT')">
						<xsl:text>;unit_label=</xsl:text>
						<xsl:value-of select="normalize-space(tradeBody/*/commodityDelivery/productFlowDetails/productFlow/deliveredQuantity/quantityUnit/unitLabel)"></xsl:value-of>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>;unit_label=</xsl:text>
						<xsl:variable name="unit_label_var" select="normalize-space(tradeBody/*/stream/capital/quantityUnit/unitLabel)"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($unit_label_var='')">
								<xsl:value-of select="tradeBody/*/stream/streamTemplate/quotationUnit/unitLabel"></xsl:value-of>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="$unit_label_var"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>

				<xsl:text>;uniform_calendar=</xsl:text>
				<xsl:variable name="uniform_calendar_var" select="normalize-space(tradeBody/*/stream/streamTemplate/floatingRateStreamTemplate/commonResetBusinessCenters)"></xsl:variable>
				<xsl:choose>
					<xsl:when test="($uniform_calendar_var='')">
						<xsl:text>false</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$uniform_calendar_var"></xsl:value-of>
					</xsl:otherwise>
				</xsl:choose>


				<!-- start strike_unit -->
					<xsl:if test="($group='FUT') or ($group='OFUT')">
						<xsl:text>;q_unit=</xsl:text>
						<xsl:value-of select="normalize-space(tradeBody/*/commodityDelivery/productFlowDetails/productFlow/deliveredQuantity/quantityUnit/unitLabel)"></xsl:value-of>
					</xsl:if>

					<xsl:if test="($group='ASIAN') or ($group='OPT')">
						<xsl:text>;q_unit=</xsl:text>
						<xsl:value-of select="tradeBody/*/stream/streamTemplate/quotationUnit/unitLabel"></xsl:value-of>
					</xsl:if>

					<xsl:if test="($group='SWAP')">
						<xsl:text>;q_unit=</xsl:text>
							<xsl:value-of select="tradeBody/*/stream/streamTemplate/quotationUnit/unitLabel"></xsl:value-of>
						<!--<xsl:variable name="payoutType_strike_unit" select="normalize-space(tradeBody/*/stream/streamTemplate/payoutType)"></xsl:variable>
						<xsl:text>;payoutType_strike_unit=</xsl:text>
						<xsl:value-of select="($payoutType_strike_unit)"></xsl:value-of>
						<xsl:if test="($payoutType_strike_unit='fixedRate')">
							<xsl:text>;q_unit=</xsl:text>
							<xsl:value-of select="tradeBody/*/stream/streamTemplate/quotationUnit/unitLabel"></xsl:value-of>
						</xsl:if>
						<xsl:if test="($payoutType_strike_unit='floatingRate')">
						</xsl:if>-->

						<xsl:text>;q_unit_margin1=</xsl:text>
						<xsl:value-of select="tradeBody/*/stream[1]/streamTemplate/quotationUnit/unitLabel"></xsl:value-of>
						<xsl:text>;q_unit_margin2=</xsl:text>
						<xsl:value-of select="tradeBody/*/stream[2]/streamTemplate/quotationUnit/unitLabel"></xsl:value-of>
					</xsl:if>
				<!-- end strike_unit -->

				<!--Default values-->
				<xsl:text>;margin1=0</xsl:text>
				<xsl:text>;margin2=0</xsl:text>


                <!--all_official-->
				<xsl:for-each select="portfolios[1]/portfolio[1]/portfolioUserDefinedFields[1]/userDefinedField">
					<xsl:variable name="all_official_var1" select="normalize-space(fieldLabel[1])"></xsl:variable>
					<xsl:variable name="all_official_var2" select="normalize-space(fieldValue[1])"></xsl:variable>
						<xsl:choose>
							<xsl:when test="($all_official_var1='OFFICIAL') and ($all_official_var2='Y')">
								<xsl:text>;all_official=true</xsl:text>							
							</xsl:when>
							<xsl:otherwise>
							</xsl:otherwise>
						</xsl:choose>
				</xsl:for-each>


				<xsl:for-each select="tradeBody/*/stream">
					<xsl:text>&#xA;</xsl:text>
					<xsl:text>[STREAM]</xsl:text>
					<xsl:for-each select="cashFlows/interestFlows/interestPaymentPeriod">
						<xsl:text>&#xA;</xsl:text>
						<xsl:text>[CASHFLOW]</xsl:text>
						<xsl:text>;leg_id=</xsl:text>
						<xsl:value-of select="position()"></xsl:value-of>

						<!--	<xsl:choose>
							<xsl:when test="($group='FUT') or ($group='OFUT')">
							</xsl:when>
							<xsl:otherwise>								
								<xsl:text>;unit_label=</xsl:text>
								<xsl:value-of select="normalize-space(../../../capital/quantityUnit/unitLabel)"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>-->

						<xsl:choose>
							<xsl:when test="($group='FUT') or ($group='OFUT')"></xsl:when>
							<xsl:otherwise>
								<xsl:text>;quantity1=</xsl:text>
								<xsl:value-of select="../../../capital/quantity * round(calculationPeriod/duration)"></xsl:value-of>
								<xsl:text>;quantity2=</xsl:text>
								<xsl:value-of select="calculationPeriod/notionalAmount * round(calculationPeriod/duration)"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>

						<xsl:choose>
							<xsl:when test="($group='ASIAN') or ($group='SWAP')or ($group='OPT')">
								<xsl:text>;first_pricing_date=</xsl:text>
								<xsl:variable name="first_pricing_date_var" select="normalize-space(calculationPeriod/floatingRatePeriod/observations/observation/timeSerieStartDate)"></xsl:variable>
								<xsl:choose>
									<xsl:when test="($first_pricing_date_var='') or ($first_pricing_date_var='00000000')">
										<!--<xsl:variable name="obsFormula" select="normalize-space(calculationPeriod/floatingRatePeriod/observations/observation/observationFormula)"></xsl:variable>
										<xsl:choose>
											<xsl:when test="($obsFormula='basket')">-->
										<xsl:value-of select="calculationPeriod/calculationStartDate"></xsl:value-of>
										<!--	</xsl:when>
											<xsl:otherwise>
											</xsl:otherwise>
										</xsl:choose>-->
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="($first_pricing_date_var)"></xsl:value-of>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
						</xsl:choose>

						<xsl:choose>
							<xsl:when test="($group='ASIAN') or ($group='SWAP')or ($group='OPT')">
								<xsl:text>;last_pricing_date=</xsl:text>
								<xsl:variable name="last_pricing_date_var" select="normalize-space(calculationPeriod/floatingRatePeriod/observations/observation/timeSerieEndDate)"></xsl:variable>
								<xsl:choose>
									<xsl:when test="($last_pricing_date_var='') or ($last_pricing_date_var='00000000')">
										<!--<xsl:variable name="obsFormula" select="normalize-space(calculationPeriod/floatingRatePeriod/observations/observation/observationFormula)"></xsl:variable>
										<xsl:choose>
											<xsl:when test="($obsFormula='basket')">-->
										<xsl:value-of select="calculationPeriod/calculationEndDate"></xsl:value-of>
										<!--	</xsl:when>
											<xsl:otherwise>
											</xsl:otherwise>
										</xsl:choose>-->
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="($last_pricing_date_var)"></xsl:value-of>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
						</xsl:choose>


						<xsl:text>;payment_date=</xsl:text>
						<xsl:value-of select="paymentDate"></xsl:value-of>

						<xsl:choose>
							<xsl:when test="($group='FUT') or ($group='OFUT') or ($group='ASIAN')">
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>;contract_month=</xsl:text>
								<xsl:value-of select="../../../maturity"></xsl:value-of>
							</xsl:otherwise>
						</xsl:choose>

						<xsl:if test="($group='ASIAN')">
							<xsl:text>;expiry=</xsl:text>
							<xsl:value-of select="paymentDate"></xsl:value-of>
						</xsl:if>

						<xsl:choose>
							<xsl:when test="($group='SWAP') or ($group='OPT')">
								<xsl:variable name="payoutType" select="normalize-space(../../../streamTemplate/payoutType)"></xsl:variable>
								<xsl:text>;payoutType=</xsl:text>
								<xsl:value-of select="($payoutType)"></xsl:value-of>
								<xsl:if test="($payoutType='fixedRate')">
									<xsl:text>;strike=</xsl:text>
									<xsl:value-of select="calculationPeriod/fixedRatePeriod/fixedRate"></xsl:value-of>
								</xsl:if>
								<xsl:if test="($payoutType='floatingRate')">
									<xsl:text>;strike=</xsl:text>
									<xsl:value-of select="../../../floatingRateStream/margin"></xsl:value-of>
								</xsl:if>
							</xsl:when>
						</xsl:choose>

						<xsl:choose>
							<xsl:when test="($group='SWAP')">
								<xsl:text>;unit_premium=</xsl:text>
								<xsl:variable name="payoutType" select="normalize-space(../../../streamTemplate/payoutType)"></xsl:variable>
								<xsl:if test="($payoutType='fixedRate')">
									<xsl:value-of select="../../../fixedRateStream/fixedRate"></xsl:value-of>
								</xsl:if>
								<xsl:if test="($payoutType='floatingRate')">
									<xsl:value-of select="../../../floatingRateStream/margin"></xsl:value-of>
								</xsl:if>
							</xsl:when>
						</xsl:choose>


						<xsl:choose>
							<xsl:when test="($group='SWAP')">
								<xsl:text>;fix_flt=</xsl:text>
								<xsl:variable name="leg0Fixed" select="count(../../../fixedRateStream)"></xsl:variable>
								<xsl:variable name="leg0Float" select="count(../../../floatingRateStream)"></xsl:variable>
								<xsl:choose>
									<xsl:when test="($leg0Fixed=1)">
										<xsl:text>FIX</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>FLT</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
						</xsl:choose>

						<!--<xsl:if test="($group='SWAP')">-->
						<xsl:text>;margin=</xsl:text>
						<xsl:value-of select="calculationPeriod/floatingRatePeriod/margin"></xsl:value-of>
						<!--</xsl:if>-->

					</xsl:for-each>
				</xsl:for-each>

				<xsl:text>&#xA;</xsl:text>
			</xsl:element>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="to-title-case">
		<xsl:param name="value"></xsl:param>
		<!-- Convert common abbreviations to uppercase -->
		<xsl:variable name="first" select="substring($value, 1, 1)"></xsl:variable>
		<xsl:variable name="remainder" select="substring($value, 2)"></xsl:variable>
		<xsl:variable name="first-upper" select="translate($first,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"></xsl:variable>
		<xsl:variable name="remainder-lower" select="translate($remainder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')"></xsl:variable>
		<xsl:value-of select="concat($first-upper, $remainder-lower)"></xsl:value-of>
	</xsl:template>
	<xsl:template name="first-upper-only">
		<xsl:param name="value"></xsl:param>
		<!-- Convert common abbreviations to uppercase -->
		<xsl:variable name="first" select="substring($value, 1, 1)"></xsl:variable>
		<xsl:variable name="first-upper" select="translate($first,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"></xsl:variable>
		<xsl:value-of select="($first-upper)"></xsl:value-of>
	</xsl:template>
	<xsl:template name="upper-case">
		<xsl:param name="value"></xsl:param>
		<xsl:variable name="upper" select="translate($value,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"></xsl:variable>
		<xsl:value-of select="($upper)"></xsl:value-of>
	</xsl:template>
</xsl:stylesheet>
