package iitb.neo.training.algorithm.lpercp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import iitb.neo.training.ds.LRGraph;
import iitb.neo.training.meta.LRGraphMemoryDataset;
import iitb.neo.training.meta.LRGraphMemoryDatasetWithoutConfusedLocationRels;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.NotImplementedException;


import org.mathison.multirframework.multiralgorithm.Dataset;
import org.mathison.multirframework.multiralgorithm.Model;
import org.mathison.multirframework.multiralgorithm.Parameters;

/**
 * Driver for the local percepton perceptron algorithm
 * @author aman
 *
 */
public class LperceptTrain {
	
	public static int numberOfAverages = 1;

	public static void train(String dir, Random r, int numIterations, double regularizer, boolean finalAvg, boolean ignoreConfusion, String mappingFile) throws IOException {		
		Model model = new Model();
		model.read(dir + File.separatorChar + "model");
		
		LocalAveragedPerceptron lpton = new LocalAveragedPerceptron(model, r, numIterations, regularizer, finalAvg, mappingFile);
		
		Dataset<LRGraph> train = null;
		if(ignoreConfusion) {
			train = (Dataset<LRGraph>) new LRGraphMemoryDatasetWithoutConfusedLocationRels(dir + File.separatorChar + "train");
		} else {
			train = (Dataset<LRGraph>) new LRGraphMemoryDataset(dir + File.separatorChar + "train");
		}

		System.out.println("starting training with regularizer = " + regularizer + ", iterations = " + numIterations + ", finalAvg = " + finalAvg + ", ignoreConfusion = " + ignoreConfusion);

		//The following is not used in NtronExperiments, and it breaks the code because the MakeAverageModel class does not exist.
		/*
		if(numberOfAverages != 1){
			List<File> randomModelFiles = new ArrayList<File>();
			for(int avgIter = 0; avgIter < numberOfAverages; avgIter++){
				
				System.out.println("Average Iteration: " + avgIter);
				
				long start = System.currentTimeMillis();
				Parameters params = lpton.train(train);
				long end = System.currentTimeMillis();
				System.out.println("training time " + (end-start)/1000.0 + " seconds");
				params.serialize(dir + File.separatorChar + "params");
				
				File newModelFile = new File(dir+"avgIter"+avgIter);
				if(!newModelFile.exists()) newModelFile.mkdir();
				randomModelFiles.add(newModelFile);
				File oldParams = new File(dir+"/params");
				File newParams = new File(newModelFile.getAbsolutePath()+"/params");
				FileUtils.copyFile(oldParams, newParams);
				
				System.gc();
			}
			MakeAverageModel.run(randomModelFiles,new File(dir));
		}
		else{ */
			long start = System.currentTimeMillis();
			Parameters params = lpton.train(train);
			long end = System.currentTimeMillis();
			System.out.println("training time " + (end-start)/1000.0 + " seconds");
	
			params.serialize(dir + File.separatorChar + "params");
		//}
	}
	
	public static void train(String dir) throws IOException {
		throw new NotImplementedException("Insufficient parameters to start training");

	}
	
	
	
	public static void main(String [] args) throws IOException{
		train(args[0]);
	}
}
