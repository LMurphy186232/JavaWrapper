package sortie.data.funcgroups;


import sortie.data.funcgroups.analysis.BoleVolumeCalculator;
import sortie.data.funcgroups.analysis.CarbonValueCalculator;
import sortie.data.funcgroups.analysis.ConditOmegaCalculator;
import sortie.data.funcgroups.analysis.DimensionAnalysis;
import sortie.data.funcgroups.analysis.FoliarChemistry;
import sortie.data.funcgroups.analysis.MerchValueCalculator;
import sortie.data.funcgroups.analysis.PartitionedDBHBiomass;
import sortie.data.funcgroups.analysis.PartitionedHeightBiomass;
import sortie.data.funcgroups.analysis.RipleysKCalculator;
import sortie.data.funcgroups.analysis.StateReporter;
import sortie.data.funcgroups.analysis.StormKilledPartitionedDBHBiomass;
import sortie.data.funcgroups.analysis.StormKilledPartitionedHeightBiomass;
import sortie.data.funcgroups.analysis.TreeAgeCalculator;
import sortie.data.funcgroups.analysis.VolumeCalculator;
import sortie.data.funcgroups.BehaviorInstantiator;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;


/**
 * Manages analysis behaviors and data. Analysis behaviors are those whose only
 * purpose is to calculate something for output; they don't change model state.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2011
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *  
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class AnalysisBehaviors extends BehaviorTypeBase {
  
	/**
	 * Constructor.
	 * 
	 * @param oManager GUIManager object.
	 * @throws ModelException passed through from called functions.
	 */
	public AnalysisBehaviors(GUIManager oManager) throws ModelException {
		super(oManager, "Analysis");
		
		String sXMLRootString, sParFileTag, sDescriptor;
	
		// Carbon value behavior
		sXMLRootString = "CarbonValueCalculator";
    sParFileTag = "CarbonValueCalculator";
    sDescriptor = "Carbon Value Calculator";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(CarbonValueCalculator.class, sDescriptor, sParFileTag, sXMLRootString));		
			  
		// Dimension analysis behavior
		sXMLRootString = "DimensionAnalysis";
    sParFileTag = "DimensionAnalysis";
    sDescriptor = "Dimension Analysis";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(DimensionAnalysis.class, sDescriptor, sParFileTag, sXMLRootString));
				
	  // Foliar chemistry behavior
		sXMLRootString = "FoliarChemistry";
    sParFileTag = "FoliarChemistry";
    sDescriptor = "Foliar Chemistry";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(FoliarChemistry.class, sDescriptor, sParFileTag, sXMLRootString));
    
		// Merchantable timber value behavior
    sXMLRootString = "MerchValueCalculator";
    sParFileTag = "MerchValueCalculator";
    sDescriptor = "Merchantable Timber Value";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(MerchValueCalculator.class, sDescriptor, sParFileTag, sXMLRootString));
		
		// Partitioned DBH Biomass behavior
		sXMLRootString = "PartitionedBiomass";
    sParFileTag = "PartitionedDBHBiomass";
    sDescriptor = "Partitioned DBH Biomass";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(PartitionedDBHBiomass.class, sDescriptor, sParFileTag, sXMLRootString));		
		
		// Partitioned Height Biomass behavior
		sXMLRootString = "PartitionedBiomass";
    sParFileTag = "PartitionedHeightBiomass";
    sDescriptor = "Partitioned Height Biomass";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(PartitionedHeightBiomass.class, sDescriptor, sParFileTag, sXMLRootString));
		
    //Relative neighborhood density (Condit's omega) calculator behavior
		sXMLRootString = "ConditsOmega";
    sParFileTag = "ConditsOmega";
    sDescriptor = "Relative Neighborhood Density Calculator";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(ConditOmegaCalculator.class, sDescriptor, sParFileTag, sXMLRootString));		
		
		// Ripley's K Calculator behavior
		sXMLRootString = "RipleysK";
    sParFileTag = "RipleysK";
    sDescriptor = "Ripley's K Calculator";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(RipleysKCalculator.class, sDescriptor, sParFileTag, sXMLRootString));
		
		// Storm Killed Partitioned DBH Biomass behavior
		sXMLRootString = "StormKilledPartitionedBiomass";
    sParFileTag = "StormKilledPartitionedDBHBiomass";
    sDescriptor = "Storm Killed Partitioned DBH Biomass";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(StormKilledPartitionedDBHBiomass.class, sDescriptor, sParFileTag, sXMLRootString));
		
		// Partitioned Height Biomass behavior
		sXMLRootString = "StormKilledPartitionedBiomass";
    sParFileTag = "StormKilledPartitionedHeightBiomass";
    sDescriptor = "Storm Killed Partitioned Height Biomass";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(StormKilledPartitionedHeightBiomass.class, sDescriptor, sParFileTag, sXMLRootString));
		
		//State variable reporter
		sXMLRootString = "StateReporter";
    sParFileTag = "StateReporter";
    sDescriptor = "State Reporter";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(StateReporter.class, sDescriptor, sParFileTag, sXMLRootString));
    
		// Tree age calculator behavior
		sXMLRootString = "TreeAgeCalculator";
    sParFileTag = "TreeAgeCalculator";
    sDescriptor = "Tree Age Calculator";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(TreeAgeCalculator.class, sDescriptor, sParFileTag, sXMLRootString));
		
		// Tree Bole Volume behavior
		sXMLRootString = "TreeBoleVolumeCalculator";
    sParFileTag = "TreeBoleVolumeCalculator";
    sDescriptor = "Tree Bole Volume Calculator";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(BoleVolumeCalculator.class, sDescriptor, sParFileTag, sXMLRootString));
		
		// Volume calculator behavior - can be edited automatically, must have trees
		sXMLRootString = "TreeVolumeCalculator";
    sParFileTag = "TreeVolumeCalculator";
    sDescriptor = "Tree Volume Calculator";
		mp_oAvailableBehaviors.add(new BehaviorInstantiator(VolumeCalculator.class, sDescriptor, sParFileTag, sXMLRootString));		
	}
}

