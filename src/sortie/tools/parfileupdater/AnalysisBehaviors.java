package sortie.tools.parfileupdater;


/**
 * Manages analysis behaviors and data. Analysis behaviors are those whose only
 * purpose is to calculate something for output; they don't change model state.
 */

public class AnalysisBehaviors extends GroupBase {

  /** Volume calculator - a in the taper equation (also known as a0) */
  protected ModelData mp_fTaperA = new ModelData("vo_taperA", "vo_taVal");

  /** Volume calculator - b in the taper equation (also known as a1) */
  protected ModelData mp_fTaperB = new ModelData("vo_taperB", "vo_tbVal");

  /** Volume calculator - c in the taper equation (also known as a2) */
  protected ModelData mp_fTaperC = new ModelData("vo_taperC", "vo_tcVal");

  /** Volume calculator - d in the taper equation (also known as b1) */
  protected ModelData mp_fTaperD = new ModelData("vo_taperD", "vo_tdVal");

  /** Volume calculator - f in the taper equation (also known as b2) */
  protected ModelData mp_fTaperF = new ModelData("vo_taperF", "vo_tfVal");

  /** Volume calculator - g in the taper equation (also known as b3) */
  protected ModelData mp_fTaperG = new ModelData("vo_taperG", "vo_tgVal");

  /** Volume calculator - i in the taper equation (also known as b4) */
  protected ModelData mp_fTaperI = new ModelData("vo_taperI", "vo_tiVal");

  /** Volume calculator - j in the taper equation (also known as b5) */
  protected ModelData mp_fTaperJ = new ModelData("vo_taperJ", "vo_tjVal");

  /** Volume calculator - k in the taper equation (also known as b6) */
  protected ModelData mp_fTaperK = new ModelData("vo_taperK", "vo_tkVal");

  /** Volume calculator - a for the diameter-outside-bark (also known as a1) */
  protected ModelData mp_fBarkA = new ModelData("vo_barkA", "vo_baVal");

  /** Volume calculator - b for the diameter-outside-bark (also known as a2) */
  protected ModelData mp_fBarkB = new ModelData("vo_barkB", "vo_bbVal");

  /** Volume calculator - c for the diameter-outside-bark (also known as a3) */
  protected ModelData mp_fBarkC = new ModelData("vo_barkC", "vo_bcVal");

  /** Dimension analysis - equation ID */
  protected ModelData mp_iEquationID = new ModelData("bi_eqID", "bi_eiVal");

  /** Dimension analysis - DBH units */
  protected ModelData mp_iDbhUnits = new ModelData("bi_dbhUnits", "bi_duVal");

  /** Dimension analysis - biomass units */
  protected ModelData mp_iBiomassUnits = new ModelData("bi_biomassUnits", "bi_buVal");

  /** Dimension analysis - whether or not to use a correction factor */
  protected ModelData mp_iUseCorrectionFactor = new ModelData("bi_useCorrectionFactor", "bi_ucfVal");

  /** Dimension analysis - correction factor value */
  protected ModelData mp_fCorrectionFactor = new ModelData("bi_correctionFactorValue", "bi_cfvVal");

  /** Dimension analysis - meaning of "dia" */
  protected ModelData mp_iDiaMeaning = new ModelData("bi_whatDia", "bi_wdVal");

  /** Dimension analysis - a in the biomass equation */
  protected ModelData mp_fBiomassA = new ModelData("bi_a", "bi_aVal");

  /** Dimension analysis - b in the biomass equation */
  protected ModelData mp_fBiomassB = new ModelData("bi_b", "bi_bVal");

  /** Dimension analysis - c in the biomass equation */
  protected ModelData mp_fBiomassC = new ModelData("bi_c", "bi_cVal");

  /** Dimension analysis - d in the biomass equation */
  protected ModelData mp_fBiomassD = new ModelData("bi_d", "bi_dVal");

  /** Dimension analysis - e in the biomass equation */
  protected ModelData mp_fBiomassE = new ModelData("bi_e", "bi_eVal");

  /** Bole volume - b0 in the volume equation */
  protected ModelData mp_fBoleVolumeB0 = new ModelData("an_boleB0", "an_bb0Val");

  /** Bole volume - b1 in the volume equation */
  protected ModelData mp_fBoleVolumeB1 = new ModelData("an_boleB1", "an_bb1Val");

  /** Bole volume - b2 in the volume equation */
  protected ModelData mp_fBoleVolumeB2 = new ModelData("an_boleB2", "an_bb2Val");

  /** Bole volume - b3 in the volume equation */
  protected ModelData mp_fBoleVolumeB3 = new ModelData("an_boleB3", "an_bb3Val");

  /** Bole volume - b4 in the volume equation */
  protected ModelData mp_fBoleVolumeB4 = new ModelData("an_boleB4", "an_bb4Val");

  /** Bole volume - b5 in the volume equation */
  protected ModelData mp_fBoleVolumeB5 = new ModelData("an_boleB5", "an_bb5Val");

  /** Bole volume - form classes */
  protected ModelData mp_fBoleVolumeFormClasses = new ModelData("an_boleFormClasses", "an_bfcVal");

  /** Merchantable timber value - form classes */
  protected ModelData mp_fMerchValueFormClasses = new ModelData("an_merchValueFormClasses", "an_mvfcVal");

  /** Merchantable timber value - price per thousand board feet */
  protected ModelData mp_fMerchValuePricePer1K = new ModelData("an_merchValuePricePer1KFeet", "an_mvpp1kfVal");

  /** Carbon value - % of biomass that is carbon */
  protected ModelData mp_fCarbonValuePercentBiomassCarbon = new ModelData("an_carbonPercentBiomassCarbon", "an_cpbcVal");

  /** Partitioned DBH biomass - Leaf DBH-biomass "a" */
  protected ModelData mp_fPartBioDbhLeafA = new ModelData("an_partBioDbhLeafA", "an_pbdlaVal");

  /** Partitioned DBH biomass - Leaf DBH-biomass "b" */
  protected ModelData mp_fPartBioDbhLeafB = new ModelData("an_partBioDbhLeafB", "an_pbdlbVal");

  /** Partitioned DBH biomass - Branch DBH-biomass "a" */
  protected ModelData mp_fPartBioDbhBranchA = new ModelData("an_partBioDbhBranchA", "an_pbdbraVal");

  /** Partitioned DBH biomass - Branch DBH-biomass "b" */
  protected ModelData mp_fPartBioDbhBranchB = new ModelData("an_partBioDbhBranchB", "an_pbdbrbVal");

  /** Partitioned DBH biomass - Bole DBH-biomass "a" */
  protected ModelData mp_fPartBioDbhBoleA = new ModelData("an_partBioDbhBoleA", "an_pbdboaVal");

  /** Partitioned DBH biomass - Bole DBH-biomass "b" */
  protected ModelData mp_fPartBioDbhBoleB = new ModelData("an_partBioDbhBoleB", "an_pbdbobVal");

  /** Foliar chemistry - foliar weight "a" */
  protected ModelData mp_fFoliarChemA  = new ModelData("an_foliarChemWeightA", "an_fcwaVal");

  /** Foliar chemistry - foliar weight "b" */
  protected ModelData mp_fFoliarChemB  = new ModelData("an_foliarChemWeightB", "an_fcwbVal");

  /** Foliar chemistry - N concentration */
  protected ModelData mp_fFoliarChemN  = new ModelData("an_foliarChemN", "an_fcnVal");

  /** Foliar chemistry - P concentration */
  protected ModelData mp_fFoliarChemP  = new ModelData("an_foliarChemP", "an_fcpVal");

  /** Foliar chemistry - lignin concentration */
  protected ModelData mp_fFoliarChemLignin  = new ModelData("an_foliarChemLignin", "an_fclVal");

  /** Foliar chemistry - fiber concentration */
  protected ModelData mp_fFoliarChemFiber  = new ModelData("an_foliarChemFiber", "an_fcfVal");

  /** Foliar chemistry - cellulose concentration */
  protected ModelData mp_fFoliarChemCellulose  = new ModelData("an_foliarChemCellulose", "an_fccVal");

  /** Foliar chemistry - tannins concentration */
  protected ModelData mp_fFoliarChemTannins  = new ModelData("an_foliarChemTannins", "an_fctVal");

  /** Foliar chemistry - phenolics concentration */
  protected ModelData mp_fFoliarChemPhenolics  = new ModelData("an_foliarChemPhenolics", "an_fcphVal");

  /** Foliar chemistry - SLA concentration */
  protected ModelData mp_fFoliarChemSLA  = new ModelData("an_foliarChemSLA", "an_fcsVal");

  /** Partitioned height biomass - Leaf height-biomass "a" */
  protected ModelData mp_fPartBioHeightLeafA = new ModelData("an_partBioHeightLeafA", "an_pbhlaVal");

  /** Partitioned height biomass - Leaf height-biomass "b" */
  protected ModelData mp_fPartBioHeightLeafB = new ModelData("an_partBioHeightLeafB", "an_pbhlbVal");

  /** Partitioned height biomass - Bole height-biomass "a" */
  protected ModelData mp_fPartBioHeightBoleA = new ModelData("an_partBioHeightBoleA", "an_pbhboaVal");

  /** Partitioned height biomass - Bole height-biomass "b" */
  protected ModelData mp_fPartBioHeightBoleB = new ModelData("an_partBioHeightBoleB", "an_pbhbobVal");

  /**
   * Volume calculator - stump height, in cm. Point at which to start summing
   * trunk volume
   */
  protected ModelData m_fStumpHeight = new ModelData("vo_stumpHeight");

  /**
   * Volume calculator - minimum usable diameter, in cm. Point at which to stop
   * summing trunk volume
   */
  protected ModelData m_fMinUsableDiam = new ModelData("vo_minUsableDiam");

  /** Volume calculator - Length of tree trunk volume segments, in m */
  protected ModelData m_fSegmentLength = new ModelData("vo_segmentLength");

  /** Carbon value - value of metric ton of carbon */
  protected ModelData m_fCarbonValueCarbonPrice = new ModelData("an_carbonPricePerMetricTonCarbon");

  /** Ripley's K - maximum distance */
  protected ModelData m_fRipleysKMaxDistance = new ModelData("an_RipleysKMaxDistance");

  /** Ripley's K - distance increment */
  protected ModelData m_fRipleysKDistanceInc = new ModelData("an_RipleysKDistanceInc");

  /** Relative neighborhood density (Condit's omega) - maximum distance */
  protected ModelData m_fConditsOmegaMaxDistance = new ModelData("an_ConditsOmegaMaxDistance");

  /** Relative neighborhood density (Condit's omega) - distance increment */
  protected ModelData m_fConditsOmegaDistanceInc = new ModelData("an_ConditsOmegaDistanceInc");


  /**
   * Constructor.
   */
  public AnalysisBehaviors() {
    super("analysis");

    int iIndex = 0;

    // Make our child behaviors
    mp_oChildBehaviors = new Behavior[14];

    // Carbon value behavior - can be edited automatically, must
    // have trees
    mp_oChildBehaviors[iIndex] = new Behavior("Carbon Value Calculator",  "CarbonValueCalculator");
    mp_oChildBehaviors[iIndex]
        .addRequiredData(mp_fCarbonValuePercentBiomassCarbon);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCarbonValueCarbonPrice);
    iIndex++;

    // Dimension analysis behavior - can be edited automatically, must have
    // trees
    mp_oChildBehaviors[iIndex] = new Behavior("tree biomass calculator",  "DimensionAnalysis");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iEquationID);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iDbhUnits);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iBiomassUnits);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iDiaMeaning);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iUseCorrectionFactor);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCorrectionFactor);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiomassA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiomassB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiomassC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiomassD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiomassE);
    iIndex++;

    // Foliar chemistry behavior - can be edited automatically, must
    // have trees
    mp_oChildBehaviors[iIndex] = new Behavior("Foliar Chemistry",  "FoliarChemistry");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemN);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemP);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemLignin);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemFiber);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemCellulose);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemTannins);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemPhenolics);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFoliarChemSLA);
    iIndex++;

    // Merchantable timber value behavior - can be edited automatically, must
    // have trees
    mp_oChildBehaviors[iIndex] = new Behavior("Merch Value Calculator",  "MerchValueCalculator");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMerchValueFormClasses);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMerchValuePricePer1K);
    iIndex++;

    // Partitioned DBH Biomass behavior - can be edited automatically, must
    // have trees
    mp_oChildBehaviors[iIndex] = new Behavior("Partitioned DBH Biomass", "PartitionedDBHBiomass", "PartitionedBiomass");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhLeafA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhLeafB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhBranchA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhBranchB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhBoleA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhBoleB);

    iIndex++;

    // Partitioned Palm Biomass behavior - can be edited automatically, must
    // have trees
    mp_oChildBehaviors[iIndex] = new Behavior("Partitioned Height Biomass", "PartitionedHeightBiomass", "PartitionedBiomass");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioHeightLeafA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioHeightLeafB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioHeightBoleA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioHeightBoleB);
    iIndex++;

    //Relative neighborhood density (Condit's omega) calculator behavior - 
    //doesn't need trees
    mp_oChildBehaviors[iIndex] = new Behavior("Condit's Omega", "ConditsOmega");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fConditsOmegaMaxDistance);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fConditsOmegaDistanceInc);
    iIndex++;

    // Ripley's K Calculator behavior - doesn't need trees
    mp_oChildBehaviors[iIndex] = new Behavior("Ripley's K", "RipleysK");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fRipleysKMaxDistance);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fRipleysKDistanceInc);
    iIndex++;

    // Partitioned DBH Biomass behavior - can be edited automatically, must
    // have trees
    mp_oChildBehaviors[iIndex] = new Behavior("Storm Killed Partitioned DBH Biomass", 
        "StormKilledPartitionedDBHBiomass", "StormKilledPartitionedBiomass");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhLeafA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhLeafB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhBranchA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhBranchB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhBoleA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioDbhBoleB);
    iIndex++;

    // Partitioned Palm Biomass behavior - can be edited automatically, must
    // have trees
    mp_oChildBehaviors[iIndex] = new Behavior("Storm Killed Partitioned Height Biomass", 
        "StormKilledPartitionedHeightBiomass", "StormKilledPartitionedBiomass");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioHeightLeafA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioHeightLeafB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioHeightBoleA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPartBioHeightBoleB);
    iIndex++;

    //State variable reporter
    mp_oChildBehaviors[iIndex] = new Behavior("State Reporter", "StateReporter");
    iIndex++;

    // Tree age calculator behavior - can be edited automatically, must have
    // trees
    mp_oChildBehaviors[iIndex] = new Behavior("Tree Age Calculator", "TreeAgeCalculator");
    // No parameters
    iIndex++;

    // Tree Bole Volume behavior - can be edited automatically, must have
    // trees
    mp_oChildBehaviors[iIndex] = new Behavior("tree bole volume calculator", "TreeBoleVolumeCalculator");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBoleVolumeFormClasses);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBoleVolumeB0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBoleVolumeB1);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBoleVolumeB2);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBoleVolumeB3);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBoleVolumeB4);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBoleVolumeB5);
    iIndex++;

    // Volume calculator behavior - can be edited automatically, must have trees
    mp_oChildBehaviors[iIndex] = new Behavior("tree volume calculator", "TreeVolumeCalculator");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTaperA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTaperB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTaperC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTaperD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTaperF);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTaperG);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTaperI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTaperJ);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTaperK);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBarkA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBarkB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBarkC);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMinUsableDiam);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fSegmentLength);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStumpHeight);
    iIndex++;

    mp_oAllData.add(mp_fTaperA);
    mp_oAllData.add(mp_fTaperB);
    mp_oAllData.add(mp_fTaperC);
    mp_oAllData.add(mp_fTaperD);
    mp_oAllData.add(mp_fTaperF);
    mp_oAllData.add(mp_fTaperG);
    mp_oAllData.add(mp_fTaperI);
    mp_oAllData.add(mp_fTaperJ);
    mp_oAllData.add(mp_fTaperK);
    mp_oAllData.add(mp_fBarkA);
    mp_oAllData.add(mp_fBarkB);
    mp_oAllData.add(mp_fBarkC);
    mp_oAllData.add(m_fMinUsableDiam);
    mp_oAllData.add(m_fSegmentLength);
    mp_oAllData.add(m_fStumpHeight);
    mp_oAllData.add(mp_iEquationID);
    mp_oAllData.add(mp_iDbhUnits);
    mp_oAllData.add(mp_iBiomassUnits);
    mp_oAllData.add(mp_iDiaMeaning);
    mp_oAllData.add(mp_iUseCorrectionFactor);
    mp_oAllData.add(mp_fCorrectionFactor);
    mp_oAllData.add(mp_fBiomassA);
    mp_oAllData.add(mp_fBiomassB);
    mp_oAllData.add(mp_fBiomassC);
    mp_oAllData.add(mp_fBiomassD);
    mp_oAllData.add(mp_fBiomassE);
    mp_oAllData.add(mp_fBoleVolumeFormClasses);
    mp_oAllData.add(mp_fBoleVolumeB0);
    mp_oAllData.add(mp_fBoleVolumeB1);
    mp_oAllData.add(mp_fBoleVolumeB2);
    mp_oAllData.add(mp_fBoleVolumeB3);
    mp_oAllData.add(mp_fBoleVolumeB4);
    mp_oAllData.add(mp_fBoleVolumeB5);
    mp_oAllData.add(mp_fMerchValueFormClasses);
    mp_oAllData.add(mp_fMerchValuePricePer1K);
    mp_oAllData.add(mp_fCarbonValuePercentBiomassCarbon);
    mp_oAllData.add(m_fCarbonValueCarbonPrice);
    mp_oAllData.add(mp_fPartBioDbhLeafA);
    mp_oAllData.add(mp_fPartBioDbhLeafB);
    mp_oAllData.add(mp_fPartBioDbhBranchA);
    mp_oAllData.add(mp_fPartBioDbhBranchB);
    mp_oAllData.add(mp_fPartBioDbhBoleA);
    mp_oAllData.add(mp_fPartBioDbhBoleB);
    mp_oAllData.add(mp_fFoliarChemA);
    mp_oAllData.add(mp_fFoliarChemB);
    mp_oAllData.add(mp_fFoliarChemN);
    mp_oAllData.add(mp_fFoliarChemP);
    mp_oAllData.add(mp_fFoliarChemLignin);
    mp_oAllData.add(mp_fFoliarChemFiber);
    mp_oAllData.add(mp_fFoliarChemCellulose);
    mp_oAllData.add(mp_fFoliarChemTannins);
    mp_oAllData.add(mp_fFoliarChemPhenolics);
    mp_oAllData.add(mp_fFoliarChemSLA);
    mp_oAllData.add(mp_fPartBioHeightLeafA);
    mp_oAllData.add(mp_fPartBioHeightLeafB);
    mp_oAllData.add(mp_fPartBioHeightBoleA);
    mp_oAllData.add(mp_fPartBioHeightBoleB);
    mp_oAllData.add(m_fRipleysKMaxDistance);
    mp_oAllData.add(m_fRipleysKDistanceInc);
    mp_oAllData.add(m_fConditsOmegaMaxDistance);
    mp_oAllData.add(m_fConditsOmegaDistanceInc);
  }
}

