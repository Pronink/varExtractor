package varExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class VarExtractor
{
	private File originFile;
	private ArrayList<VarExtracted> varList;
	private char separator = '=';
	private int defaultInt = -1;
	private long defaultLong = -1;
	private float defaultFloat = -1.0F;
	private boolean defaultBoolean = false;
	private String defaultString = null;

	// /**
	// * <h1>varExtractor</h1>
	// * <p>
	// * Extract a simple text file of variables into a list that you can manage
	// * easily with our methods
	// * </p>
	// *
	// * @param originFile
	// * The existing file witch will be used to manage variables. Only
	// * use if you manage the file. Else use VarExtractor(String
	// * originFile).
	// * @author Ismael García Torres
	// */
	// public VarExtractor(File originFile)
	// {
	// this.originFile = originFile;
	// varList = new ArrayList<>();
	// }

	/**
	 * <h1>varExtractor</h1>
	 * <p>
	 * Extract a simple text file of variables into a list that you can manage
	 * easily with our methods
	 * </p>
	 * <p>
	 * Defaults:
	 * <ul>
	 * <li>defaultSeparator: '='</li>
	 * <li>defaultInt: -1</li>
	 * <li>defaultLong: -1</li>
	 * <li>defaultFloat: -1.0</li>
	 * <li>defaultBoolean: false</li>
	 * <li>defaultString: null</li>
	 * </ul>
	 * </p>
	 * 
	 * @param originFile
	 *            Path of the file. If file doesn't exist, then it creates a new
	 *            one.
	 * @author Ismael García Torres
	 */
	public VarExtractor(String originFile)
	{
		this.originFile = new File(originFile);
		try
		{
			this.originFile.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		varList = new ArrayList<>();
	}

	/**
	 * <h1>varExtractor - getSeparator</h1>
	 * <p>
	 * Gets the default char separator between name and value of text file.
	 * </p>
	 * <p>
	 * Default separator is '='.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public char getSeparator()
	{
		return separator;
	}

	/**
	 * <h1>varExtractor - setSeparator</h1>
	 * <p>
	 * Sets the default char separator between name and value of text file.
	 * </p>
	 * <p>
	 * Default separator is '='.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setSeparator(char separator)
	{
		this.separator = separator;
	}

	/**
	 * <h1>varExtractor - setDefaultInt</h1>
	 * <p>
	 * Sets a different default int returned by getInt() if there is an error.
	 * </p>
	 * <p>
	 * Default int is -1.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setDefaultInt(int defaultInt)
	{
		this.defaultInt = defaultInt;
	}

	/**
	 * <h1>varExtractor - setDefaultLong</h1>
	 * <p>
	 * Sets a different default long returned by getLong() if there is an error.
	 * </p>
	 * <p>
	 * Default long is -1.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setDefaultLong(long defaultLong)
	{
		this.defaultLong = defaultLong;
	}

	/**
	 * <h1>varExtractor - setDefaultFloat</h1>
	 * <p>
	 * Sets a different default float returned by getFloat() if there is an
	 * error.
	 * </p>
	 * <p>
	 * Default float is -1.1
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setDefaultFloat(float defaultFloat)
	{
		this.defaultFloat = defaultFloat;
	}

	/**
	 * <h1>varExtractor - setDefaultBoolean</h1>
	 * <p>
	 * Sets a different default boolean returned by getBoolean() if there is an
	 * error.
	 * </p>
	 * <p>
	 * Default boolean is false.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setDefaultBoolean(boolean defaultBoolean)
	{
		this.defaultBoolean = defaultBoolean;
	}

	/**
	 * <h1>varExtractor - setDefaultString</h1>
	 * <p>
	 * Sets a different default String returned by getString() if there is an
	 * error.
	 * </p>
	 * <p>
	 * Default String is null.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setDefaultString(String defaultString)
	{
		this.defaultString = defaultString;
	}

	/**
	 * <h1>varExtractor - loadVariables</h1>
	 * <p>
	 * Loads all variables from the text file to memory. The modifications
	 * changes only in memory. To save them to the text file again, simply use
	 * saveVariables().
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void loadVariables()
	{
		// Loads the variables from file and generates introduce them into
		// "varList" ArrayList<VarExtracted>
		varList.clear();
		BufferedReader via = null;
		try
		{
			via = new BufferedReader(new FileReader(originFile));
			String line;
			while ((line = via.readLine()) != null)
			{
				if (line.length() > 0)
				{
					varList.add(new VarExtracted(line.substring(0, line.indexOf(separator)),
							line.substring(line.indexOf(separator) + 1)));
					System.out.println("Loaded: " + line.substring(0, line.indexOf(separator)) + separator
							+ line.substring(line.indexOf(separator) + 1, line.length()));
				}
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR:\tFile " + originFile + " not found");
		}
		catch (IOException e)
		{
			System.out.println("ERROR:\tIOException");
		}
		finally
		{
			try
			{
				via.close();
			}
			catch (IOException e)
			{
				System.out.println("ERROR:\tFile can't be closed");
			}
		}
	}

	/**
	 * <h1>varExtractor - saveVariables</h1>
	 * <p>
	 * Save all the variables in memory to the text file, overwriting it.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void saveVariables()
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(new FileWriter(originFile));
			for (int i = 0; i < varList.size(); i++)
			{
				writer.println(varList.get(i).getName() + separator + varList.get(i).getValue());
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR:\tFile " + originFile + " not found");
		}
		catch (IOException e)
		{
			System.out.println("ERROR:\tIOException");
		}
		finally
		{
			writer.close();
		}
	}

	private String getValueOf(String name)
	{
		for (int i = 0; i < varList.size(); i++)
		{
			if (varList.get(i).getName().equals(name))
			{
				return varList.get(i).getValue();
			}
		}
		return null;
	}

	/**
	 * <h1>varExtractor - getInt</h1>
	 * <p>
	 * Search the name of variable and return his int value.
	 * </p>
	 * <p>
	 * If there is an error, it will return default int. To change default int,
	 * use setDefaultInt().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public int getInt(String name)
	{
		String value = getValueOf(name);
		if (value != null)
		{
			try
			{
				return Integer.parseInt(value);
			}
			catch (Exception e)
			{
				System.out.println("ERROR: " + name + " isn't int. Returning default int: " + defaultInt);
				return defaultInt;
			}
		}
		else
		{
			System.out.println("ERROR: " + name + " not found. Returning default int: " + defaultInt);
			return defaultInt;
		}
	}

	/**
	 * <h1>varExtractor - getLong</h1>
	 * <p>
	 * Search the name of variable and return his long value.
	 * </p>
	 * <p>
	 * If there is an error, it will return default long. To change default
	 * long, use setDefaultLong().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public long getLong(String name)
	{
		String value = getValueOf(name);
		if (value != null)
		{
			try
			{
				return Long.parseLong(value);
			}
			catch (Exception e)
			{
				System.out.println("ERROR: " + name + " isn't long. Returning default long: " + defaultLong);
				return defaultLong;
			}
		}
		else
		{
			System.out.println("ERROR: " + name + " not found. Returning default long: " + defaultLong);
			return defaultLong;
		}
	}

	/**
	 * <h1>varExtractor - getFloat</h1>
	 * <p>
	 * Search the name of variable and return his float value.
	 * </p>
	 * <p>
	 * If there is an error, it will return default float. To change default
	 * float, use setDefaultFloat().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public float getFloat(String name)
	{
		String value = getValueOf(name);
		if (value != null)
		{
			try
			{
				return Float.parseFloat(value);
			}
			catch (Exception e)
			{
				System.out.println("ERROR: " + name + " isn't float. Returning default float: " + defaultFloat);
				return defaultFloat;
			}
		}
		else
		{
			System.out.println("ERROR: " + name + " not found. Returning default float: " + defaultFloat);
			return defaultFloat;
		}
	}

	/**
	 * <h1>varExtractor - getBoolean</h1>
	 * <p>
	 * Search the name of variable and return his boolean value.
	 * </p>
	 * <p>
	 * If there is an error, it will return default boolean. To change default
	 * boolean, use setDefaultBoolean().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public boolean getBoolean(String name)
	{
		String value = getValueOf(name);
		if (value != null)
		{
			if (value.toLowerCase().equals("true"))
			{
				return true;
			}
			else if (value.toLowerCase().equals("false"))
			{
				return false;
			}
			else
			{
				System.out.println("ERROR: " + name + " isn't boolean. Returning default boolean: " + defaultBoolean);
				return defaultBoolean;
			}
		}
		else
		{
			System.out.println("ERROR: " + name + " not found. Returning default boolean: " + defaultBoolean);
			return defaultBoolean;
		}
	}

	/**
	 * <h1>varExtractor - getString</h1>
	 * <p>
	 * Search the name of variable and return his String value.
	 * </p>
	 * <p>
	 * If there is an error, it will return default String. To change default
	 * String, use setDefaultString().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public String getString(String name)
	{
		String value = getValueOf(name);
		if (value != null)
		{
			return value;
		}
		else
		{
			System.out.println("ERROR: " + name + " not found. Returning default String: " + defaultString);
			return defaultString;
		}
	}

	private void setValueOf(String name, String value)
	{
		boolean found = false;
		for (int i = 0; i < varList.size(); i++)
		{
			if (varList.get(i).getName().equals(name))
			{
				varList.get(i).setValue(value);
				found = true;
			}
		}
		// If one or more variable's names found, no print any error message
		if (found == false)
		{
			System.out.println(
					"ERROR: " + name + " not found. Try using method addVariable(\"" + name + "\", \"newValue\")");
		}
	}

	/**
	 * <h1>varExtractor - setInt</h1>
	 * <p>
	 * Sets the value of named variable to the new int specified.
	 * </p>
	 * <p>
	 * The variable must exist. To create a new variable, use addVariable().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setInt(String name, int value)
	{
		setValueOf(name, String.valueOf(value));
	}

	/**
	 * <h1>varExtractor - setLong</h1>
	 * <p>
	 * Sets the value of named variable to the new long specified.
	 * </p>
	 * <p>
	 * The variable must exist. To create a new variable, use addVariable().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setLong(String name, long value)
	{
		setValueOf(name, String.valueOf(value));
	}

	/**
	 * <h1>varExtractor - setFloat</h1>
	 * <p>
	 * Sets the value of named variable to the new float specified.
	 * </p>
	 * <p>
	 * The variable must exist. To create a new variable, use addVariable().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setFloat(String name, float value)
	{
		setValueOf(name, String.valueOf(value));
	}

	/**
	 * <h1>varExtractor - setBoolean</h1>
	 * <p>
	 * Sets the value of named variable to the new boolean specified.
	 * </p>
	 * <p>
	 * The variable must exist. To create a new variable, use addVariable().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setBoolean(String name, boolean value)
	{
		if (value == true)
		{
			setValueOf(name, "true");
		}
		else
		{
			setValueOf(name, "false");
		}
	}

	/**
	 * <h1>varExtractor - setString</h1>
	 * <p>
	 * Sets the value of named variable to the new String specified.
	 * </p>
	 * <p>
	 * The variable must exist. To create a new variable, use addVariable().
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void setString(String name, String value)
	{
		setValueOf(name, String.valueOf(value));
	}

	/**
	 * <h1>varExtractor - addVariable</h1>
	 * <p>
	 * Adds a new variable with his value.
	 * </p>
	 * <p>
	 * The variable type only depends when the variable is extracted.
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void addVariable(String name, String value)
	{
		varList.add(new VarExtracted(name, value));
	}

	/**
	 * <h1>varExtractor - removeVariable</h1>
	 * <p>
	 * Search and removes all variables whose name is indicated.
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void removeVariable(String name)
	{
		for (int i = 0; i < varList.size(); i++)
		{
			if (varList.get(i).getName() == name)
			{
				varList.remove(i);
			}
		}
	}

	/**
	 * <h1>varExtractor - sortVariables</h1>
	 * <p>
	 * Sort all variables by name.
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void sortVariables()
	{
		// I create a list of variables's name and I sort it
		ArrayList<String> stringList = new ArrayList<>();
		for (int i = 0; i < varList.size(); i++)
		{
			stringList.add(varList.get(i).getName());
		}
		Collections.sort(stringList, String.CASE_INSENSITIVE_ORDER);

		// I create a new arrayList "cacheList" with combination of sorted
		// variable's name and the searched value from original "varList"
		ArrayList<VarExtracted> cacheList = new ArrayList<>();
		for (int i = 0; i < stringList.size(); i++)
		{
			cacheList.add(new VarExtracted(stringList.get(i), getValueOf(stringList.get(i))));
		}

		// Finally "cacheList" replaces "varList"
		varList = cacheList;
	}

	/**
	 * <h1>varExtractor - testExistenceOf</h1>
	 * <p>
	 * Return true if searched name variable exists.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public boolean testExistenceOf(String name)
	{
		if (getValueOf(name) == null)
		{
			return false;
		}
		return true;
	}

	/**
	 * <h1>varExtractor - testDuplicates</h1>
	 * <p>
	 * Return true if there are duplicated name variables.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public boolean testDuplicates()
	{
		String nameCursor;
		for (int i = 0; i < varList.size() - 1; i++)
		{
			nameCursor = varList.get(i).getName();
			for (int j = i + 1; j < varList.size(); j++)
			{
				if (varList.get(j).getName().equals(nameCursor))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <h1>varExtractor - removeDuplicates</h1>
	 * <p>
	 * Remove all duplicate variables names and values. It keeps the first
	 * duplicate founded.
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void removeDuplicates()
	{
		String nameCursor;
		for (int i = 0; i < varList.size() - 1; i++)
		{
			nameCursor = varList.get(i).getName();
			// System.out.println("Comparando " + nameCursor + " con:");
			for (int j = i + 1; j < varList.size(); j++)
			{
				// System.out.println(" " + varList.get(j).getName());
				if (varList.get(j).getName().equals(nameCursor))
				{
					// System.out.println(" --> Borrando " +
					// varList.get(j).getName());
					varList.remove(j);
					j--;
				}
			}
		}
	}

	/**
	 * <h1>varExtractor - removeAllVariables</h1>
	 * <p>
	 * Remove all variables names and values.
	 * </p>
	 * <p>
	 * Remember using loadVariables() to load all variables before start
	 * managing variables. Then, you can use saveVariables() to save changes
	 * into the text file.
	 * </p>
	 * 
	 * @author Ismael García Torres
	 */
	public void removeAllVariables()
	{
		varList.clear();
	}

}
