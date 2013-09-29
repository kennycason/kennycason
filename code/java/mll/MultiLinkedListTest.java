package test;

import mll.MultiLinkedList;

public class MultiLinkedListTest {

  /**
   * Constructor for MLLTest.
   * @param name
   */
  public MultiLinkedListTest() {
  }

  
  /**
   * just a temp to make testing faster
   * @param args
   */
  public static void main(String[] args) {
	  MultiLinkedListTest mllTest = new MultiLinkedListTest();
	  mllTest.InitMLLTest();
  }
	
	public void InitMLLTest() {
		MultiLinkedList<Double> mll = new MultiLinkedList<Double>();
		Double[] data = new Double[4];
		data[0] = 1.0;
		data[1] = 2.0;
		data[2] = 3.0;
		data[3] = 4.0;
		mll.append(data);
		Double[] data2 = new Double[6];
		data2[0] = 3.0;
		data2[1] = 4.0;
		data2[2] = 5.0;
		data2[3] = 6.0;
		data2[4] = 7.0;
		data2[5] = 8.0;
		mll.append(data2);
		
		Double[] data3 = new Double[4];
		data3[0] = 8.0;
		data3[1] = 2.0;
		data3[2] = 3.0;
		data3[3] = 5.0;
		mll.append(data3);
		//System.out.println(mll.toString());
		
		// test MLLNode.isEqual()
		if(mll.getRoot().get(0).getValue().equals(data[0])) {
			System.out.println("mll.getRoot().get(0).isEqual(data[0]) = TRUE - PASS");
		} else {
			System.out.println("mll.getRoot().get(0).isEqual(data[0]) = FALSE - FAIL");
		}
		if(mll.getRoot().get(0).get(0).getValue().equals(data[1])) {
			System.out.println("mll.getRoot().get(0).get(0).isEqual(data[1]) = TRUE - PASS");
		} else {
			System.out.println("mll.getRoot().get(0).get(0).isEqual(data[1]) = FALSE - FAIL");
		}

		if(!mll.getRoot().get(0).getValue().equals(123)) {
			System.out.println("mll.getRoot().get(0).isEqual(123) = FALSE - PASS");
		} else {
			System.out.println("mll.getRoot().get(0).isEqual(123) = TRUE - FAIL");
		}
		
		
		// test MLLNode.find()
		if(mll.find(1.0) != null) {
			System.out.println("mll.find(1) != NULL - PASS");
		} else {
			System.out.println("mll.find(1) = NULL - FAIL");
		}
		if(mll.find(2.0) != null) {
			System.out.println("mll.find(2) != NULL - PASS");
		} else {
			System.out.println("mll.find(2) = NULL - FAIL");
		}
		if(mll.find(3.0) != null) {
			System.out.println("mll.find(3) != NULL - PASS");
		} else {
			System.out.println("mll.find(3) = NULL - FAIL");
		}
		if(mll.find(4.0) != null) {
			System.out.println("mll.find(4) != NULL - PASS");
		} else {
			System.out.println("mll.find(4) = NULL - FAIL");
		}
		//System.out.println(mll.getRoot().get(0).getValue());
		//System.out.println(mll.getRoot().get(0).get(0).getValue());
		//System.out.println(mll.getRoot().get(0).get(0).getPrev().get(0));
		//System.out.println(mll.getRoot().get(0).get(0).get(0).getValue());
		//System.out.println(mll.getRoot().get(0).get(0).get(0).get(0).getValue());

		// test MLLNode.find()
		if(mll.contains(data)) {
			System.out.println("mll.find(data) = TRUE - PASS");
		} else {
			System.out.println("mll.find(data) = FALSE - FAIL");
		}
		 
		// test adding same 
		mll.append(mll.getRoot(),5.0);
		mll.append(mll.getRoot(),5.0);
		// 5 is the second value connected to the value, {0,5}, so it array index
		// is 1, and since it has been added twice the weight should be 1
		/*if(mll.getRoot().getWeight(1) == 1) {
			System.out.println("mll.getRoot().getWeight(1) == 1 - PASS");
		} else {
			System.out.println("mll.getRoot().getWeight(1) != 1 - FAIL");
		}*/
		mll.find(4.0).add(mll.find(5.0));
		mll.find(3.0).add(mll.find(5.0));
		System.out.println(mll.toString());
		
		
	}
	
}
