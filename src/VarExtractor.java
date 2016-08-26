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

	/**
	 * <h1>varExtractor</h1> Extract a simple text file of variables into a list
	 * that you can manage easily with our methods
	 * <p>
	 * <b>Note:</b> WIP
	 *
	 * @author
	 * @version
	 * @since
	 */
	public VarExtractor(File originFile)
	{
		this.originFile = originFile;
		varList = new ArrayList<>();
	}

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

	public char getSeparator()
	{
		return separator;
	}

	public void setSeparator(char separador)
	{
		this.separator = separador;
	}

	public int getDefaultInt()
	{
		return defaultInt;
	}

	public void setDefaultInt(int defaultInt)
	{
		this.defaultInt = defaultInt;
	}

	public long getDefaultLong()
	{
		return defaultLong;
	}

	public void setDefaultLong(long defaultLong)
	{
		this.defaultLong = defaultLong;
	}

	public float getDefaultFloat()
	{
		return defaultFloat;
	}

	public void setDefaultFloat(float defaultFloat)
	{
		this.defaultFloat = defaultFloat;
	}

	public boolean getDefaultBoolean()
	{
		return defaultBoolean;
	}

	public void setDefaultBoolean(boolean defaultBoolean)
	{
		this.defaultBoolean = defaultBoolean;
	}

	public String getDefaultString()
	{
		return defaultString;
	}

	public void setDefaultString(String defaultString)
	{
		this.defaultString = defaultString;
	}

	// Loads the variables from file and generates introduce them into
	// "varList" ArrayList<VarExtracted>
	public void loadVariables()
	{
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
					/*
					 * System.out.println(line.substring(0,
					 * line.indexOf(separator)) + "=" +
					 * line.substring(line.indexOf(separator) + 1,
					 * line.length()));
					 */
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

	/** Gets float from preloaded list of variables */
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

	public void setInt(String name, int value)
	{
		setValueOf(name, String.valueOf(value));
	}

	public void setLong(String name, long value)
	{
		setValueOf(name, String.valueOf(value));
	}

	public void setFloat(String name, float value)
	{
		setValueOf(name, String.valueOf(value));
	}

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

	public void setString(String name, String value)
	{
		setValueOf(name, String.valueOf(value));
	}

	public void addVariable(String name, String value)
	{
		varList.add(new VarExtracted(name, value));
	}

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

	public boolean testExistenceOf(String name)
	{
		if (getValueOf(name) == null)
		{
			return false;
		}
		return true;
	}

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

	public void removeAllVariables()
	{
		varList.clear();
	}

}
