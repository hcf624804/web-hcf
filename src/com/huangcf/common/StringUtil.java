package com.huangcf.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.BreakIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;


public class StringUtil
{

    private static final char QUOTE_ENCODE[] = "&quot;".toCharArray();
    private static final char AMP_ENCODE[] = "&amp;".toCharArray();
    private static final char LT_ENCODE[] = "&lt;".toCharArray();
    private static final char GT_ENCODE[] = "&gt;".toCharArray();
    private static Object initLock = new Object();
    private static MessageDigest digest = null;
    private static final String commonWords[] = {
        "a", "and", "as", "at", "be", "do", "i", "if", "in", "is",
        "it", "so", "the", "to"
    };
    private static Map commonWordsMap = null;
    private static Random randGen = null;
    private static char numbersAndLetters[] = null;
    private static final char zeroArray[] = "0000000000000000".toCharArray();
   
    
    public StringUtil()
    {
    	
    }
    
    
    /**
     * get date
     * @param n the date add count
     * @return n days after current date
     */
    public static final String addDate(int n)throws Exception
    {
        String strDate=new String();
        String strMonth=new String();
        String strYear=new String();
        String strDay=new String();
        int iDay=n;
        String dateFormat = new String();
        dateFormat += "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat (dateFormat);
        Calendar cal=Calendar.getInstance();

	try
	{
            strDate=getCurDateTime();
            strMonth=getMonth(strDate);
            strDay=getDay(strDate);
            iDay=iDay+stringToInt(strDay);
            strDay=Integer.toString(iDay);
            strYear=getYear(strDate);
            strMonth=getMonth(strDate);
            strDate=strYear+"-"+strMonth+"-"+strDay;
            Date d1=formatter.parse(strDate);
            cal.setTime(d1);
            strYear=Integer.toString(cal.get( Calendar.YEAR  ));
            strMonth=Integer.toString(cal.get(Calendar.MONTH) +1);
            strDay=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            strDate=strYear+"-"+strMonth+"-"+strDay;

	}
	catch(Exception de)
	{
            System.err.println("addDate()  error "+de.getMessage());
            de.printStackTrace();
	}
	return strDate;
    }
    /**
     * get date
     * @param date the date
     * @param n the date add count
     * @return n days after date
     */
    public static final String addDate(String date,int n)throws Exception
    {
        String strDate=date;
        String strMonth=new String();
        String strYear=new String();
        String strDay=new String();
        int iDay=n;
        String dateFormat = new String();
        dateFormat += "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat (dateFormat);
        Calendar cal=Calendar.getInstance();

	try
	{
            strMonth=getMonth(strDate);
            strDay=getDay(strDate);
            iDay=iDay+stringToInt(strDay);
            strDay=Integer.toString(iDay);
            strYear=getYear(strDate);
            strMonth=getMonth(strDate);
            strDate=strYear+"-"+strMonth+"-"+strDay;
            Date d1=formatter.parse(strDate);
            cal.setTime(d1);
            strYear=Integer.toString(cal.get( Calendar.YEAR  ));
            strMonth=Integer.toString(cal.get(Calendar.MONTH) +1);
            strDay=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            strDate=strYear+"-"+strMonth+"-"+strDay;
	}
	catch(Exception de)
	{
            System.err.println("Convert: addDate()"+de.getMessage());
	    de.printStackTrace();
	}
	return strDate;
    }
    /**
     * get date
     * @param n the date add count
     * @return n days after current date
     */
    public static final String addFullDate(String date, int n)throws Exception
    {
	String strDate="";
	try
	{
            n=0-n*24*60;
	    strDate=getReminderTime(date,n);
        }
	catch(Exception de)
	{
	    System.err.println("Convert: addDate()"+de.getMessage());
	    de.printStackTrace();
	}
        return strDate;
    }
public static final String asHTML(String text) {
	  if (text == null) return "";
	  StringBuffer results = null;
	  char[] orig = null;
	  int beg = 0, len = text.length();
	  for (int i = 0; i < len; ++i){
		  char c = text.charAt(i);
		  switch (c){
			  case 0:
			  case '&':
			  case '<':
			  case '>':
			  case '"':
			  if (results == null){
				 orig = text.toCharArray();
				 results = new StringBuffer(len+10);
			  }
			  if (i > beg) results.append(orig, beg, i-beg);
				 beg = i + 1;
				 switch (c){
					default: // case 0:
					continue;
				 case '&':
					results.append("&amp;");
					break;
				 case '<':
					results.append("&lt;");
					break;
				 case '>':
					results.append("&gt;");
					break;
				 case '"':
					results.append("&quot;");
					break;
				 }
				 break;
		   }
	  }
	  if (results == null)
	  return text;
	  results.append(orig, beg, len-beg);
	  return results.toString();
   }
    /**
     * check user's telephone
     * @param userPhone user's telephone number
     * @return true if the phone is correct
     */
    public static final boolean checkPhone(String userPhone)
    {
        boolean bl=false;
        String phone=userPhone;
        int i=0;
        char ch;
        if(phone.length()<5)
            return false;
	do
	{
	    ch=phone.charAt(i);
	    switch (ch)
	    {
                case '-':
                case '(':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                case ')':
                {
		    bl=true;
		    break;
		}
		default:
		{
		    bl=false;
		    break;
		}
	    }
	    i++;
	}
        while(i<phone.length()&&bl);
	switch(phone.charAt(phone.length()-1))
	{
	    case '-':
	    case '(':
	    {
	        bl=false;
		break;
	    }
        }

	return bl;
    }
    /**
     * check user's zip code
     * @param userZipCode user's Zip Code
     * @return true if the zip code is correct
     */
    public static final boolean checkZipCode(String userZipCode)
    {
        boolean bl=false;
        String zipCode=userZipCode;
        int i=0;
        char ch;
        if(zipCode.length()>10)
                return false;
        do
        {
            ch=zipCode.charAt(i);
            switch (ch)
            {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
		{
        	    bl=true;
		    break;
		}
		default:
		{
		    bl=false;
		    break;
		}
            }
	    i++;
	}
	while(i<zipCode.length()&&bl);
	return bl;
    }
    public static final String chopAtWord(String string, int length)
    {
        if(string == null)
            return string;
        char charArray[] = string.toCharArray();
        int sLength = string.length();
        if(length < sLength)
            sLength = length;
        for(int i = 0; i < sLength - 1; i++)
        {
            if(charArray[i] == '\r' && charArray[i + 1] == '\n')
                return string.substring(0, i + 1);
            if(charArray[i] == '\n')
                return string.substring(0, i);
        }

        if(charArray[sLength - 1] == '\n')
            return string.substring(0, sLength - 1);
        if(string.length() < length)
            return string;
        for(int i = length - 1; i > 0; i--)
            if(charArray[i] == ' ')
                return string.substring(0, i).trim();

        return string.substring(0, length);
    }
    public static final String convertReturn(String str)throws Exception
    {
        int index=-1;
        int begin=0;
        int end=0;
        String string=str;
        //str=setNull(str);
        if(str==null)
	{
            return "";
	}
	String newstr=str;
	index=string.indexOf("\r");
	if(index!=-1)
	{
	    end=index;
	    index++;
	    newstr=string.substring(begin,end)+"<br>";
	    begin=index+1;
	    while(((index=string.indexOf("\r",index))!=-1)&&(index<=string.length()))
	    {
		end=index;
		index++;
		newstr=newstr+string.substring(begin,end)+"<br>";
		begin=index+1;
	    }
	    if((index==-1)&&((end+2)<=string.length()))
	    {
		newstr=newstr+string.substring(end+2);
	    }
	}
        return newstr;
    }
    public static final String convertString(String str,String str1,String str2)throws Exception
    {
        int index=-1;
        int begin=0;
        int end=0;
        String string=str;
        string=setNull(string);
        if(string==null)
        {
            return "";
        }
        String string1=str1;
        String string2=str2;
        String newstr=str;
        index=string.indexOf(string1);
        if(index!=-1)
        {
            end=index;
            index++;
            newstr=string.substring(begin,end)+string2;
            begin=index+string1.length()-1;
            while(((index=string.indexOf(string1,index))!=-1)&&(index<=string.length()))
            {
                end=index;
                index++;
                newstr=newstr+string.substring(begin,end)+string2;
                begin=index+string1.length()-1;
            }
            if((index==-1)&&((end+string1.length())<=string.length()))
            {
                    newstr=newstr+string.substring(end+string1.length());
            }
        }
	return newstr;

    }
public static final String convertToBr(String Content){
 	  if (Content == null) return "";
		 String makeContent=new String();
		 StringTokenizer strToken=new StringTokenizer(Content,"\n");
		 while(strToken.hasMoreTokens()){
			makeContent=makeContent+"<br>"+strToken.nextToken();
		 }
	  return makeContent;
   }
    public static final String convertToReturn(String str)throws Exception
    {
	int index=-1;
	int begin=0;
	int end=0;
	String string=str;
	str=setNull(str);
	if(str==null)
	{
	    return "";
	}
        String newstr=str;
	index=string.indexOf("<br>");
	if(index!=-1)
	{
            end=index;
            index++;
            newstr=string.substring(begin,end)+"\r";
            begin=index+3;
            while(((index=string.indexOf("<br>",index))!=-1)&&(index<=string.length()))
            {
                end=index;
                index++;
                newstr=newstr+string.substring(begin,end)+"\r";
                begin=index+3;
            }
            if((index==-1)&&((end+4)<=string.length()))
            {
                newstr=newstr+string.substring(end+4);
            }
        }
        return newstr;

    }
    public static final String dateToMillis(Date date)
    {
        return zeroPadString(Long.toString(date.getTime()), 15);
    }
    public static String decodeBase64(byte data[])
    {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len * 3) / 4);
        for(int i = 0; i < len; i++)
        {
            int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            i++;
            int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            c = c << 2 | c1 >> 4 & 3;
            ret.append((char)c);
            if(++i < len)
            {
                c = data[i];
                if(61 == c)
                    break;
                c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char)c);
                c1 = c1 << 4 & 0xf0 | c >> 2 & 0xf;
                ret.append((char)c1);
            }
            if(++i >= len)
                continue;
            c1 = data[i];
            if(61 == c1)
                break;
            c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char)c1);
            c = c << 6 & 0xc0 | c1;
            ret.append((char)c);
        }

        return ret.toString();
    }
    public static String decodeBase64(String data)
    {
        return decodeBase64(data.getBytes());
    }
    public static final byte[] decodeHex(String hex)
    {
        char chars[] = hex.toCharArray();
        byte bytes[] = new byte[chars.length / 2];
        int byteCount = 0;
        for(int i = 0; i < chars.length; i += 2)
        {
            byte newByte = 0;
            newByte |= hexCharToByte(chars[i]);
            newByte <<= 4;
            newByte |= hexCharToByte(chars[i + 1]);
            bytes[byteCount] = newByte;
            byteCount++;
        }

        return bytes;
    }
    public static final String decrypt(String string)
    {
	String str=string;
	int i=-1;
	i=str.length();
	char ch[]=new char[i];
	str.getChars(0,i,ch,0);
	String str1=new String();

	for(i=0;i<str.length();i++)
	{
	    ch[i]=(char)(((short)ch[i]+3)/2-1);
	    str1+=ch[i];
	}

	return str1;
    }
    public static String encodeBase64(byte data[])
    {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);
        for(int i = 0; i < len; i++)
        {
            int c = data[i] >> 2 & 0x3f;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            c = data[i] << 4 & 0x3f;
            if(++i < len)
                c |= data[i] >> 4 & 0xf;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            if(i < len)
            {
                c = data[i] << 2 & 0x3f;
                if(++i < len)
                    c |= data[i] >> 6 & 3;
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else
            {
                i++;
                ret.append('=');
            }
            if(i < len)
            {
                c = data[i] & 0x3f;
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else
            {
                ret.append('=');
            }
        }

        return ret.toString();
    }
    public static String encodeBase64(String data)
    {
        return encodeBase64(data.getBytes());
    }
    public static final String encodeHex(byte bytes[])
    {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for(int i = 0; i < bytes.length; i++)
        {
            if((bytes[i] & 0xff) < 16)
                buf.append("0");
            buf.append(Long.toString(bytes[i] & 0xff, 16));
        }

        return buf.toString();
    }
    public static final String encrypt(String string)
    {

	String str=string;
	int i=-1;
	i=str.length();
	char ch[]=new char[i];
	str.getChars(0,i,ch,0);
	String str1=new String();

	for(i=0;i<str.length();i++)
	{
	    ch[i]=(char)(((short)ch[i]+1)*2-3);
	    str1+=ch[i];
	}

	return str1;
    }
    public static final String escapeForXML(String string)
    {
        if(string == null)
            return null;
        int i = 0;
        int last = 0;
        char input[] = string.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int)((double)len * 1.3D));
        for(; i < len; i++)
        {
            char ch = input[i];
            if(ch <= '>')
                if(ch == '<')
                {
                    if(i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(LT_ENCODE);
                } else
                if(ch == '&')
                {
                    if(i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(AMP_ENCODE);
                } else
                if(ch == '"')
                {
                    if(i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(QUOTE_ENCODE);
                }
        }

        if(last == 0)
            return string;
        if(i > last)
            out.append(input, last, i - last);
        return out.toString();
    }
    public static final String escapeHTMLTags(String in)
    {
        if(in == null)
            return null;
        int i = 0;
        int last = 0;
        char input[] = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int)((double)len * 1.3D));
        for(; i < len; i++)
        {
            char ch = input[i];
            if(ch <= '>')
                if(ch == '<')
                {
                    if(i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(LT_ENCODE);
                } else
                if(ch == '>')
                {
                    if(i > last)
                        out.append(input, last, i - last);
                    last = i + 1;
                    out.append(GT_ENCODE);
                }
        }

        if(last == 0)
            return in;
        if(i > last)
            out.append(input, last, i - last);
        return out.toString();
    }
    public static final String getCurDate()
    {
        String dateFormat = new String();
        dateFormat += "MMMM dd, yyyy";
	SimpleDateFormat formatter = new SimpleDateFormat (dateFormat,
	Locale.getDefault());
	String currentDateTime = formatter.format(new Date());
        return currentDateTime;
    }
    public static final String getCurDateTime()
    {

        String dateFormat = new String();
	dateFormat += "yyyy-MM-dd HH:mm:ss.SSS";
	SimpleDateFormat formatter = new SimpleDateFormat (dateFormat,Locale.getDefault());
        String currentDateTime = formatter.format(new Date());
        return currentDateTime;
    }
    public static final String getCurDateTime(int timeZone)
    {
        int timeZoneMinute=0-timeZone*60;
        String currentDateTime="";
        try
    	{
            currentDateTime = getCurDateTime();
            currentDateTime=getReminderTime(currentDateTime,timeZoneMinute);
        }
        catch(Exception de)
	{
              System.err.println("getCurDateTime(int timeZone) error"+de.getMessage());
              de.printStackTrace();
        }
	return 	currentDateTime;

    }
    public static final String getCurDateTime(int start, int end)
    {

        String dateFormat = new String();
	dateFormat += "yyyy-MM-dd HH:mm:ss.SSS";
	SimpleDateFormat formatter = new SimpleDateFormat (dateFormat,Locale.getDefault());
        String currentDateTime = formatter.format(new Date());
	currentDateTime = currentDateTime.substring(start,end);
        return currentDateTime;
    }
    public static final String getCurDateTimeNoSpace()
    {

        String dateFormat = new String();
        dateFormat += "yyyyMMddHHmmssSSS";
        SimpleDateFormat formatter = new SimpleDateFormat (dateFormat,
            Locale.getDefault());
        String currentDateTime = formatter.format(new Date());
        return currentDateTime;
    }
    /**
     * get date by min
     * @param date
     * @param newdate
     */
    public static final String getDateByMin(String date)
    {
        String strDate=date;
        String newDate=new String();
        try
        {
            newDate=getYear(strDate)+"-"+getMonth(strDate)+"-"+
                        getDay(strDate)+" "+getHour(strDate)+":"+
                        getMinute(strDate);
        }
        catch(Exception e)
        {
            System.out.println("getDateByMin() error "+e.getMessage());
            e.printStackTrace() ;
        }
        return newDate;
    }
    /**
     * get day from a string
     * @param date the string include datetime
     * @return day  the day in the string date
     * @exception  Exception when error occur.
     */
    public static final String getDay(String date)throws Exception
    {

        String strDate=date;
        String day=new String();
        String dateFormat = new String();
        Calendar cal=Calendar.getInstance();
        try
	{
            dateFormat += "yyyy-MM-dd";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(strDate);
	    cal.setTime(d1);
	    day=Integer.toString(cal.get( Calendar.DAY_OF_MONTH ));
	}
        catch(Exception de)
	{
    	    System.err.println("getDay() error "+de.getMessage());
            de.printStackTrace() ;
            return null;
	}
        return day;
    }
    /**
     * get day from a string
     * @param date the string include datetime
     * @return day  the day in the string date
     * @exception  Exception when error occur.
     */
    public static final String getDayEX(String date)throws Exception
    {

        String strDate=date;
        String day=new String();
        String dateFormat = new String();
        Calendar cal=Calendar.getInstance();
        try
        {
            dateFormat += "MM/dd/yyyy";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(strDate);
            cal.setTime(d1);
            day=Integer.toString(cal.get( Calendar.DAY_OF_MONTH ));
        }

        catch(Exception de)
        {
            System.err.println("getDayex() error "+de.getMessage());
            de.printStackTrace() ;
            return null;

        }
        return day;
    }
/**
 * Insert the method's description here.
 * Creation date: (2002-7-26 17:15:56)
 * @return int
 * @param month java.lang.String
 * @param year java.lang.String
 */
public static final int getDayOfMonth(String month, String year) {
	if(month.indexOf("0")!=-1)
	{
		month.substring(1,2);
		}
	int day = 0;
	int[] days_in_month = {31,28,31,30,31,30,31,31,30,31,30,31};
	int iyear = Integer.parseInt(year);
	int imonth = Integer.parseInt(month)-1;
	if ((iyear%4 == 0) && (iyear%100!=0) || (iyear%400 == 0))
	{
    	days_in_month[1] = 29;
    	}
	else
	{
    	days_in_month[1] = 28;
    	}
    day = days_in_month[imonth];

	return day;
}
    /**
     * get default expire date
     *
     * @return one month after current date
     *
     */
    public static final String getDefaultExpireDate()throws Exception
    {
        String strDate=new String();
        String strMonth=new String();
        String strYear=new String();
        String strDay=new String();
        int iMonth=-1;
        String dateFormat = new String();
        dateFormat += "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat (dateFormat);
        Calendar cal=Calendar.getInstance();

        try
        {
            strDate=getCurDateTime();
            strMonth=getMonth(strDate);
            iMonth=stringToInt(strMonth);
            iMonth++;
            strMonth=Integer.toString(iMonth);
            strYear=getYear(strDate);
            strDay=getDay(strDate);
            strDate=strYear+"-"+strMonth+"-"+strDay;
            Date d1=formatter.parse(strDate);
            cal.setTime(d1);
            strYear=Integer.toString(cal.get( Calendar.YEAR  ));
            strMonth=Integer.toString(cal.get(Calendar.MONTH) +1);
            strDay=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            strDate=strYear+"-"+strMonth+"-"+strDay;

        }
        catch(Exception de)
        {
            System.err.println("getDefaultExpireDate() error "+de.getMessage());
            de.printStackTrace();
        }
        return strDate;
    }
    /**
     * get default expire date
     * @param date Date object
     * @return one month after current date
     */
    public static final String getDefaultExpireDate(String date)throws Exception
    {
        String strDate=date;
        String strMonth=new String();
        String strYear=new String();
        String strDay=new String();
        int iMonth=-1;
        String dateFormat = new String();
        dateFormat += "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat (dateFormat);
        Calendar cal=Calendar.getInstance();
	try
        {
            strDate=getCurDateTime();
            strMonth=getMonth(strDate);
            iMonth=stringToInt(strMonth);
            iMonth++;
            strMonth=Integer.toString(iMonth);
            strYear=getYear(strDate);
            strDay=getDay(strDate);
            strDate=strYear+"-"+strMonth+"-"+strDay;
            Date d1=formatter.parse(strDate);
            cal.setTime(d1);
            strYear=Integer.toString(cal.get( Calendar.YEAR  ));
            strMonth=Integer.toString(cal.get(Calendar.MONTH) +1);
            strDay=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            strDate=strYear+"-"+strMonth+"-"+strDay;

	}
	catch(Exception de)
	{
            System.err.println("getDefaultExpireDate()  error "+de.getMessage());
	    de.printStackTrace();
	}
	    return strDate;
    }
    /**
     * get hour from a string
     * @param date the string include datetime
     * @return hour  the hour in the string date
     * @exception  Exception when error occur.
     */
    public static final String getHour(String date)throws Exception
    {

        String strDate=date;
        String hour=new String();
        String dateFormat = new String();
        Calendar cal=Calendar.getInstance();
        try
	{
            dateFormat += "yyyy-MM-dd HH:mm:ss.SSS";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(strDate);
	    cal.setTime(d1);
	    hour=Integer.toString(cal.get( Calendar.HOUR_OF_DAY));
	}
        catch(Exception de)
	{
            System.err.println("getDay() error "+de.getMessage());
            de.printStackTrace() ;
            return null;
	}
        return hour;
    }
    /**
     * get minute from a string
     * @param date the string include datetime
     * @return hour  the minute in the string date
     * @exception  Exception when error occur.
     *
     */
    public  static final String getMinute(String date)throws Exception
    {

        String strDate=date;
        int imin=0;
        String minute=new String();
        String dateFormat = new String();
        Calendar cal=Calendar.getInstance();
        try
	{
            dateFormat += "yyyy-MM-dd HH:mm:ss.SSS";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(strDate);
	    cal.setTime(d1);
	    imin=cal.get( Calendar.MINUTE );
	    if(imin<10)
	    {
		minute="0"+Integer.toString(imin);
	    }
	    else
	    {
		minute=Integer.toString(imin);
	    }
	}
	catch(Exception de)
	{
	    System.err.println("getDay() error "+de.getMessage());
	    de.printStackTrace() ;
            return null;
	}
        return minute;
    }
    /**
     * get month from a string
     * @param date the string include datetime
     * @return month the month in the string date
     * @exception  Exception when error occur.
     */
    public static final String getMonth(String date)throws Exception
    {

        String strDate=date;
        String month=new String();
        String dateFormat = new String();
        Calendar cal=Calendar.getInstance();
        try
	{
            dateFormat += "yyyy-MM-dd";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(strDate);
	    cal.setTime(d1);
	    month=Integer.toString( cal.get(Calendar.MONTH)+1);
        }
        catch(Exception de)
	{
	    System.err.println("getMonth() error"+de.getMessage());
            de.printStackTrace() ;
            return null;
        }
        return month;
    }
    /**
     * get month from a string
     * @param date the string include datetime
     * @return month the month in the string date
     * @exception  Exception when error occur.
     */
    public static final String getMonthEX(String date)throws Exception
    {

        String strDate=date;
        String month=new String();
        String dateFormat = new String();
        Calendar cal=Calendar.getInstance();
        try
        {
            dateFormat += "MM/dd/yyyy";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(strDate);
            cal.setTime(d1);
            month=Integer.toString( cal.get(Calendar.MONTH)+1);
        }

        catch(Exception de)
        {
            System.err.println("getMonthex() error "+de.getMessage());
            de.printStackTrace() ;
            return null;

        }
        return month;
    }
    public static final String getRandom()
    {
	Random rd=new Random();
	String str=new String();
	String str1=new String();
	str=rd.toString();
	str1=str.substring(17,str.length());
	str1.trim();
	return str1;
    }
    /**
     * get date time
     *
     * @param dateTime
     * @param n how many minutes should be decrease
     * @return the dateTime of the return
     *
     */
    public static final String getReminderTime(String dateTime,int minute)throws Exception
    {
        String strDate=dateTime;
        String strMonth=new String();
        String strYear=new String();
        String strDay=new String();
        String strHour=new String();
        String strMinute=new String();

        int iDay;
        int iHour;
        int iMinute;

        if(minute>=0)
	{
            iDay=minute/1440;
            iHour=(minute-(iDay*1440))/60;
            if(iHour<0) iHour=0;
            iMinute=(minute-(iDay*1440)-iHour*60);
            if(iMinute<0) iMinute=0;
        }
	else
	{
            minute=0-minute;
            iDay=minute/1440;
            iHour=(minute-(iDay*1440))/60;
            if(iHour<0) iHour=0;
            iMinute=(minute-(iDay*1440)-iHour*60);
            if(iMinute<0) iMinute=0;
            minute=0-minute;
	}

        String dateFormat = new String();
        dateFormat += "yyyy-MM-dd HH:mm:ss.SSS";
        SimpleDateFormat formatter = new SimpleDateFormat (dateFormat);
        Calendar cal=Calendar.getInstance();

        try
        {
            strMonth=getMonth(strDate);
            strDay=getDay(strDate);

            if(minute>0)
            {
		iDay=stringToInt(strDay)-iDay;
	  	iHour=stringToInt(getHour(strDate))-iHour;
	        iMinute=stringToInt(getMinute(strDate))-iMinute;
            }
	    else
	    {
		iDay=stringToInt(strDay)+iDay;
	  	iHour=stringToInt(getHour(strDate))+iHour;
	        iMinute=stringToInt(getMinute(strDate))+iMinute;
            }

	    strHour=Integer.toString(iHour);
            strDay=Integer.toString(iDay);
	    strMinute=Integer.toString(iMinute);
            strYear=getYear(strDate);
            strMonth=getMonth(strDate);
            strDate=strYear+"-"+strMonth+"-"+strDay+" "+strHour+":"+strMinute+":"+"12.000"  ;

            Date d1=formatter.parse(strDate);
            cal.setTime(d1);
            strYear=Integer.toString(cal.get( Calendar.YEAR  ));
            strMonth=Integer.toString(cal.get(Calendar.MONTH) +1);
            strDay=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            strHour=Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
            strMinute=Integer.toString(cal.get(Calendar.MINUTE));
            strDate=strYear+"-"+strMonth+"-"+strDay+" "+strHour+":"+strMinute+":00.000"  ;

        }
	catch(Exception de)
	{
	    System.err.println("getReminderTime() error "+de.getMessage());
	    de.printStackTrace();
        }
	return strDate;
    }
    /**
     * get second from a string
     * @param date the string include datetime
     * @return hour  the second in the string date
     * @exception  Exception when error occur.
     *
     */
    public static final String getSecond(String date)throws Exception
    {

        String strDate=date;
        String second=new String();
        String dateFormat = new String();
        Calendar cal=Calendar.getInstance();
        try
	{
            dateFormat += "yyyy-MM-dd HH:mm:ss.SSS";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(strDate);
            cal.setTime(d1);
	    second=Integer.toString(cal.get( Calendar.SECOND ));
	}
        catch(Exception de)
	{
	    System.err.println("getSecond() error "+de.getMessage());
            de.printStackTrace() ;
            return null;
	}
        return second;
	}
    /**
     * get year from a string
     * @param date the string include datetime
     * @return year the year in the string date
     * @exception  Exception when error occur.
     */
    public static final String getYear(String date)throws Exception
    {
	String strDate=date;
	String year=new String();
	String dateFormat = new String();
	Calendar cal=Calendar.getInstance();
        try
	{
            dateFormat += "yyyy-MM-dd";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(strDate);
            cal.setTime(d1);
	    year=Integer.toString( cal.get(Calendar.YEAR ));
	}
        catch(Exception de)
	{
            System.err.println("getYear() error"+de.getMessage());
            de.printStackTrace() ;
            return null;
        }
        return year;
    }
    /**
     * get year from a string
     * @param date the string include datetime
     * @return year the year in the string date
     * @exception  Exception when error occur.
     */
    public static final String getYearEX(String date)throws Exception
    {

        String strDate=date;
        String year=new String();
        String dateFormat = new String();
        Calendar cal=Calendar.getInstance();

        try
        {
            dateFormat += "MM/dd/yyyy";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(strDate);
            cal.setTime(d1);
            year=Integer.toString( cal.get(Calendar.YEAR ));
        }

        catch(Exception de)
        {
            System.err.println("getYearex() error "+de.getMessage());
            de.printStackTrace() ;
            return null;

        }
        return year;
    }
    public static final synchronized String hash(String data)
    {
        if(digest == null)
            try
            {
                digest = MessageDigest.getInstance("MD5");
            }
            catch(NoSuchAlgorithmException nsae)
            {
                System.err.println("Failed to load the MD5 MessageDigest.  it will be unable to function normally.");
                nsae.printStackTrace();
            }
        digest.update(data.getBytes());
        return encodeHex(digest.digest());
    }
    private static final byte hexCharToByte(char ch)
    {
        switch(ch)
        {
        case 48: // '0'
            return 0;

        case 49: // '1'
            return 1;

        case 50: // '2'
            return 2;

        case 51: // '3'
            return 3;

        case 52: // '4'
            return 4;

        case 53: // '5'
            return 5;

        case 54: // '6'
            return 6;

        case 55: // '7'
            return 7;

        case 56: // '8'
            return 8;

        case 57: // '9'
            return 9;

        case 97: // 'a'
            return 10;

        case 98: // 'b'
            return 11;

        case 99: // 'c'
            return 12;

        case 100: // 'd'
            return 13;

        case 101: // 'e'
            return 14;

        case 102: // 'f'
            return 15;

        case 58: // ':'
        case 59: // ';'
        case 60: // '<'
        case 61: // '='
        case 62: // '>'
        case 63: // '?'
        case 64: // '@'
        case 65: // 'A'
        case 66: // 'B'
        case 67: // 'C'
        case 68: // 'D'
        case 69: // 'E'
        case 70: // 'F'
        case 71: // 'G'
        case 72: // 'H'
        case 73: // 'I'
        case 74: // 'J'
        case 75: // 'K'
        case 76: // 'L'
        case 77: // 'M'
        case 78: // 'N'
        case 79: // 'O'
        case 80: // 'P'
        case 81: // 'Q'
        case 82: // 'R'
        case 83: // 'S'
        case 84: // 'T'
        case 85: // 'U'
        case 86: // 'V'
        case 87: // 'W'
        case 88: // 'X'
        case 89: // 'Y'
        case 90: // 'Z'
        case 91: // '['
        case 92: // '\\'
        case 93: // ']'
        case 94: // '^'
        case 95: // '_'
        case 96: // '`'
        default:
            return 0;
        }
    }
    
    /**
     * 身份证15转成18位
     * @param p_zjhm 身份证号码
     * @return
     */
	public static final String id15to18(String p_zjhm)
    {
       
        String     r_zjhm18 = p_zjhm;
        String     r_18;
        String     r_power  = "0709100508040201060307091005080402";
        int        r_result = 0 ;

    
        if (r_zjhm18!=null) r_zjhm18=r_zjhm18.toUpperCase();

        if (p_zjhm!=null) {
            if (r_zjhm18.length()!=15 && r_zjhm18.length()!=18 ) {
                return null;
            }
        }

        if (r_zjhm18.length()==15) {   
            r_zjhm18 = r_zjhm18.substring(0,6) + "19" + r_zjhm18.substring(6,15); 
            for (int i=1;i<=17;i++) {
                r_result = r_result + Integer.parseInt(r_zjhm18.substring(i-1,i))*Integer.parseInt(r_power.substring(2*i-2,2*i));
            }
            r_result = r_result % 11;

            switch (r_result) {
                case 0:
                r_18="1";
                break;
                case 1:
                r_18="0";
                break;
                case 2:
                r_18="X";
                break;
                case 3:
                r_18="9";
                break;
                case 4:
                r_18="8";
                break;
                case 5:
                r_18="7";
                break;
                case 6:
                r_18="6";
                break;
                case 7:
                r_18="5";
                break;
                case 8:
                r_18="4";
                break;
                case 9:
                r_18="3";
                break;
                case 10:
                r_18="2";
                break;
                default:
                r_18="e";
            } // end of switch

            //wonders.Util.SysUtil.MsgStdOut("r_18 ="+r_18 );  //debug

            if (r_18.equals("e")) {
                return null;
            } else {
                r_zjhm18 = r_zjhm18 + r_18;
            }
        }
        return r_zjhm18;
    }
	
	/**
	 * 身份证18位转成15位
	 * @param p_zjhm
	 * @return
	 */
    public static final String id18to15(String p_zjhm)
    {
   
        String     r_zjhm18 = p_zjhm;

   
        if (r_zjhm18!=null) r_zjhm18=r_zjhm18.toUpperCase();

        if (p_zjhm!=null) {
            if (r_zjhm18.length()!=15 && r_zjhm18.length()!=18 ) {   
                return null;
            }
        }

        if (r_zjhm18.length()==18) { 
            r_zjhm18 = r_zjhm18.substring(0,6) + r_zjhm18.substring(8,17);  
        }

        return r_zjhm18;
    }
    /**
     * check if the time is after the preset time	*
     * @param strTime  the string of the time to be tested
     * @param strPresetTime  the string of the presetTime
     * @return true if the the strTime is after the strPresetTime
     * @exception  Exception when error occur.
     */
    public static final boolean isAfter(String strTime, String strPresetTime)throws Exception
    {
        boolean result=false;
        String dateFormat = new String();
       
        try
	{
            dateFormat += "yyyy-MM-dd HH:mm:ss.SSS";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date start=format.parse(strTime);
            Date preset=format.parse(strPresetTime);
            result=start.after(preset);

	}
	catch(Exception de)
	{
        	System.err.println("isAfter() error "+de.getMessage());
		de.printStackTrace();
	}
        return result;
    }
    /**
     * get one month ago date
     * @param date Date object
     * @return one month before  date
     */
    public static final String oneMonthAgo(String date)throws Exception
    {
        String strDate=date;
        String strMonth=new String();
        String strYear=new String();
        String strDay=new String();
        int iMonth=-1;
        String dateFormat = new String();
        dateFormat += "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat (dateFormat);
        Calendar cal=Calendar.getInstance();


	try
	{
            strMonth=getMonth(strDate);
	    iMonth=stringToInt(strMonth);
	    iMonth--;
	    strMonth=Integer.toString(iMonth);
	    strYear=getYear(strDate);
	    strDay=getDay(strDate);
            strDate=strYear+"-"+strMonth+"-"+strDay;
            Date d1=formatter.parse(strDate);
            cal.setTime(d1);
            strYear=Integer.toString(cal.get( Calendar.YEAR  ));
            strMonth=Integer.toString(cal.get(Calendar.MONTH) +1);
            strDay=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            strDate=strYear+"-"+strMonth+"-"+strDay;

        }
	catch(Exception de)
	{
	    System.err.println("getDefaultExpireDate() error "+de.getMessage());
	    de.printStackTrace();
	}
	return strDate;
    }
    public static final String randomString(int length)
    {
        if(length < 1)
            return null;
        if(randGen == null)
            synchronized(initLock)
            {
                if(randGen == null)
                {
                    randGen = new Random();
                    numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
                }
            }
        char randBuffer[] = new char[length];
        for(int i = 0; i < randBuffer.length; i++)
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];

        return new String(randBuffer);
    }
    public static final String[] removeCommonWords(String words[])
    {
        if(commonWordsMap == null)
            synchronized(initLock)
            {
                if(commonWordsMap == null)
                {
                    commonWordsMap = new HashMap();
                    for(int i = 0; i < commonWords.length; i++)
                        commonWordsMap.put(commonWords[i], commonWords[i]);

                }
            }
        ArrayList results = new ArrayList(words.length);
        for(int i = 0; i < words.length; i++)
            if(!commonWordsMap.containsKey(words[i]))
                results.add(words[i]);

        return (String[])results.toArray(new String[results.size()]);
    }
    public static final String replace(String line, String oldString, String newString)
    {
        if(line == null || line.equals(""))
            return "";
        int i = 0;
        if((i = line.indexOf(oldString, i)) >= 0)
        {
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for(j = i; (i = line.indexOf(oldString, i)) > 0; j = i)
            {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            return buf.toString();
        } else
        {
            return line;
        }
    }
    public static final String replace(String line, String oldString, String newString, int count[])
    {
    	if(line == null || line.equals(""))
            return "";
        int i = 0;
        if((i = line.indexOf(oldString, i)) >= 0)
        {
            int counter = 0;
            counter++;
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for(j = i; (i = line.indexOf(oldString, i)) > 0; j = i)
            {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        } else
        {
            return line;
        }
    }
    public static final String replaceIgnoreCase(String line, String oldString, String newString)
    {
    	if(line == null || line.equals(""))
            return "";
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if((i = lcLine.indexOf(lcOldString, i)) >= 0)
        {
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for(j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i)
            {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            return buf.toString();
        } else
        {
            return line;
        }
    }
    public static final String replaceIgnoreCase(String line, String oldString, String newString, int count[])
    {
    	if(line == null || line.equals(""))
            return "";
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if((i = lcLine.indexOf(lcOldString, i)) >= 0)
        {
            int counter = 0;
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for(j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i)
            {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        } else
        {
            return line;
        }
    }
    public static final String setNull(String string)
    {
	String str=string;
	if(str!=null)
	{
            if(str.toLowerCase().compareTo("null")==0)
	        str=null;
	    if(str.compareTo("")==0)
	        str=null;
        }
	return str;
    }
//    public static final int stringToInt(String stringValue)
//    {
//		int value=1;
//	        Integer i= new Integer(0);
//		if(stringValue!=null)
//	        {
//	            try
//	            {
//		        value = Integer.parseInt(stringValue);
//	            }
//		    catch(Exception de)
//		    {
//		        System.err.println("Failed to convert string "+ stringValue+" to Int");
//	                de.printStackTrace();
//	            }
//		}
//
//	return 	value;
//    }
    /**
     * get days between two days the format is day1-day2
     * @param day1
     * @param day2
     * @return i
     */
    public static final int subDays(String day1,String day2)
    {

        long l=0;
        long l1=0;
        long l2=0;
        int i=0;
        String str1=day1;
        String str2=day2;
        String dateFormat = new String();
        try
        {
            dateFormat += "yyyy-MM-dd";
            SimpleDateFormat format=new SimpleDateFormat(dateFormat );
            Date d1=format.parse(str1);
		l1=d1.getTime();
            Date d2=format.parse(str2);
		l2=d2.getTime();
            l=l1-l2;
            l=l/(24*60*60*1000);
            i=(int)l;
	}
        catch(Exception e)
	{
	    e.printStackTrace();
	}
        return i;
    }
    
	public static String toGB2312(String s) throws Exception {
		if (s != null && s.length() > 0) {
			byte[] byteTmp = s.getBytes("ISO8859_1");
			s = new String(byteTmp, "GB2312");
		}
		return s;
	}
    public static final String toGB(String string)
    {
        String str1=new String();
        String str=new String();
        byte[] by=string.getBytes();
	try
	{

            for(int f=0;f<by.length-1;f++)
            {
                if(by[f]<0)
                {
                    str1=new String(by,f,2,"GB2312");
                    str+=str1;
                    f++;
                }
                else
                {
                    str1=new String(by,f,1,"GB2312");
                    str+=str1;
                }

                if(by[by.length-1]>=0)
                {
                    str1=new String(by,by.length-1,1,"GB2312");
                    str+=str1;
                }
            }

        }
	catch(Exception e)
	{
	    System.err.println("toGB() error "+e.getMessage());
            e.printStackTrace() ;
	}
        return str;
    }
    public static final String[] toLowerCaseWordArray(String text)
    {
        if(text == null || text.length() == 0)
            return new String[0];
        ArrayList wordList = new ArrayList();
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(text);
        int start = 0;
        for(int end = boundary.next(); end != -1; end = boundary.next())
        {
            String tmp = text.substring(start, end).trim();
            tmp = replace(tmp, "+", "");
            tmp = replace(tmp, "/", "");
            tmp = replace(tmp, "\\", "");
            tmp = replace(tmp, "#", "");
            tmp = replace(tmp, "*", "");
            tmp = replace(tmp, ")", "");
            tmp = replace(tmp, "(", "");
            tmp = replace(tmp, "&", "");
            if(tmp.length() > 0)
                wordList.add(tmp);
            start = end;
        }

        return (String[])wordList.toArray(new String[wordList.size()]);
    }
    public static final String unescapeFromXML(String string)
    {
        string = replace(string, "&lt;", "<");
        string = replace(string, "&gt;", ">");
        string = replace(string, "&quot;", "\"");
        return replace(string, "&amp;", "&");
    }
    public static final String zeroPadString(String string, int length)
    {
        if(string == null || string.length() > length)
        {
            return string;
        } else
        {
            StringBuffer buf = new StringBuffer(length);
            buf.append(zeroArray, 0, length - string.length()).append(string);
            return buf.toString();
        }
    }
    
	/**
	 * Encode the url to UTF format.
	 * 
	 * @param s
	 *            The target URL
	 * @param sp
	 *            the sperate taf.
	 * @return The url after encode.
	 */
	public static String chineseURLToUTF(String s, char sp) {
		String filename = (new File(s)).getName();
		String path = (new File(s)).getParent();

		path = path.replace('\\', sp);
		try {
			filename = java.net.URLEncoder.encode(filename, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		path = path + "/" + filename;
		return path;
	}
	
	/**
	 * Encode the string in html format.
	 * 
	 * @param s
	 *            The string.
	 * @return The string in html format.
	 */
	public static String getHTMLString(String s) {
		if (s == null)
			return ("");
		if (s.equals(""))
			return ("");

		StringBuffer buf = new StringBuffer();
		char ch1 = '\n';
		char ch2 = '\n';

		for (int i = 0; i < s.length(); i++) {
			ch1 = s.charAt(i);

			if ((ch1 == ' ') && ((i + 1) < s.length())) {
				ch2 = s.charAt(i + 1);
				if (ch2 == ' ') {
					buf.append("\u00A1\u00A1");
					i++;
				} else {
					buf.append(ch1);
				}
			} else if (ch1 == '\n') {
				buf.append("<br>");
			} else if (ch1 == '\t') {
				buf.append("\u00A1\u00A1\u00A1\u00A1");
			} else {
				buf.append(ch1);
			}
		}

		return buf.toString();
	}

	/**
	 * Encode the string in html format.
	 * 
	 * @param t
	 *            The target text
	 * @return the string after encode.
	 */
	public static String encodeHTML(String t) {
		t = t.replaceAll("&", "@amp;");
		t = t.replaceAll("\"", "@quot;");
		t = t.replaceAll("<", "@lt;");
		t = t.replaceAll(">", "@gt;");
		t = t.replaceAll("'", "@#146;");
		t = t.replaceAll(" ", "@nbsp;");
		t = t.replaceAll("#", "%23");
		return t;
	}
	/**
	 * Decode the html format.
	 * 
	 * @param t
	 *            The target format.
	 * @return The string after decode.
	 */
	public static String dencodeHTML(String t) {
		t = t.replaceAll("&amp;", "&");
		t = t.replaceAll("&quot;", "\"");
		t = t.replaceAll("&lt;", "<");
		t = t.replaceAll("&gt;", ">");
		t = t.replaceAll("&#146;", "'");
		t = t.replaceAll("&nbsp;", " ");
		t = t.replaceAll("&#13;", "\n");
		t = t.replaceAll("&#10;", "\n");

		t = t.replaceAll("@amp;", "&");
		t = t.replaceAll("@quot;", "\"");
		t = t.replaceAll("@lt;", "<");
		t = t.replaceAll("@gt;", ">");
		t = t.replaceAll("@#146;", "'");
		t = t.replaceAll("@nbsp;", " ");
		t = t.replaceAll("&#9;", " ");
		t = t.replaceAll("%23", "#");
		return t;
	}

	/**
	 * Check whether the speical string include the chinese word.
	 * 
	 * @param str
	 *            The speical string
	 * @return true for include , false otherwise.
	 * @throws Exception
	 */
	public static boolean haveChinesewords(String str) throws Exception {
		return !toGB2312(str).equals(str);
	}

	/**
	 * Get the same sub string between two string.
	 * 
	 * @param s1
	 *            The first string.
	 * @param s2
	 *            The second string.
	 * @return The same sub string
	 */
	public static String getSameString(String s1, String s2) {
		String s = "";
		if (s1 == null || s1.trim().length() <= 0 || s2 == null
				|| s2.trim().length() <= 0)
			return s;

		int len = s1.length() > s2.length() ? s2.length() : s1.length();
		System.out.println("len->" + len);
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();
		for (int i = 0; i < len; i++) {
			if (c1[i] == c2[i]) {
				s += c1[i];
				continue;
			} else {
				break;
			}
		}
		return s;
	}

	public static String getFixLengthString(String str, int length)
			throws Exception { // ***************
		if (str == null || str.trim().length() < 0)
			return getBlankString(length);
		String reStr = "";
		// 去掉回车
		str = str.replaceAll("\r", "");
		str = str.replaceAll("\n", "");
		str = new String(str.getBytes(), "8859_1");
		if (str.length() >= length) {
			reStr = str.substring(0, length);
		} else {
			reStr = str + getBlankString(length - str.length());
			// System.out.println("length:"+length+" restr's
			// length:"+reStr.length());
		}
		byte[] bytesStr = reStr.getBytes("8859_1");
		reStr = new String(bytesStr, "gb2312");
		return reStr;
	}

	public static String getBlankString(int count) {
		String str = "";
		for (int i = 0; i < count; i++) {
			str = str + " ";
		}
		return str;
	}


    
    public static String dateToString(Date date){
    	
    	if(date == null) return new String("");
    	
    	String dateFormat = new String();
    	dateFormat += "yyyy-MM-dd HH:mm:ss";
    	SimpleDateFormat formatter = new SimpleDateFormat (dateFormat,Locale.getDefault());
        String currentDateTime = formatter.format(date);
        return currentDateTime;
    }

    /// Add By Jacky 
    
    public static String ENCODING_DEFAULT = "ISO-8859-1";
    public static String GET_ENCODING_DEFAULT = "ISO-8859-1";
    public static String FILE_WRITING_ENCODING = "GBK";


    public static boolean isEmpty(String _string)
    {
        return _string == null || _string.trim().length() == 0;
    }

    public static boolean isEmptyStr(String _string)
    {
        return _string == null || _string.trim().length() == 0;
    }

    public static String showNull(String p_sValue)
    {
        return showNull(p_sValue, "");
    }

    public static String showNull(String _sValue, String _sReplaceIfNull)
    {
        return _sValue != null ? _sValue : _sReplaceIfNull;
    }

    public static String expandStr(String _string, int _length, char _chrFill, boolean _bFillOnLeft)
    {
        int nLen = _string.length();
        if(_length <= nLen)
            return _string;
        String sRet = _string;
        for(int i = 0; i < _length - nLen; i++)
            sRet = _bFillOnLeft ? String.valueOf(_chrFill) + String.valueOf(sRet) : String.valueOf(sRet) + String.valueOf(_chrFill);

        return sRet;
    }

    public static String setStrEndWith(String _string, char _chrEnd)
    {
        if(_string == null)
            return null;
        if(_string.charAt(_string.length() - 1) != _chrEnd)
            return String.valueOf(_string) + String.valueOf(_chrEnd);
        else
            return _string;
    }

    public static String makeBlanks(int _length)
    {
        if(_length < 1)
            return "";
        StringBuffer buffer = new StringBuffer(_length);
        for(int i = 0; i < _length; i++)
            buffer.append(' ');

        return buffer.toString();
    }

    public static String replaceStr(String _strSrc, String _strOld, String _strNew)
    {
        if(_strSrc == null)
            return null;
        char srcBuff[] = _strSrc.toCharArray();
        int nSrcLen = srcBuff.length;
        if(nSrcLen == 0)
            return "";
        char oldStrBuff[] = _strOld.toCharArray();
        int nOldStrLen = oldStrBuff.length;
        if(nOldStrLen == 0 || nOldStrLen > nSrcLen)
            return _strSrc;
        StringBuffer retBuff = new StringBuffer(nSrcLen * (1 + _strNew.length() / nOldStrLen));
        boolean bIsFound = false;
        for(int i = 0; i < nSrcLen;)
        {
            bIsFound = false;
            if(srcBuff[i] == oldStrBuff[0])
            {
                int j;
                for(j = 1; j < nOldStrLen && i + j < nSrcLen && srcBuff[i + j] == oldStrBuff[j]; j++);
                bIsFound = j == nOldStrLen;
            }
            if(bIsFound)
            {
                retBuff.append(_strNew);
                i += nOldStrLen;
            } else
            {
                int nSkipTo;
                if(i + nOldStrLen >= nSrcLen)
                    nSkipTo = nSrcLen - 1;
                else
                    nSkipTo = i;
                while(i <= nSkipTo) 
                {
                    retBuff.append(srcBuff[i]);
                    i++;
                }
            }
        }

        return retBuff.toString();
    }

    
   
    public static String toISO_8859(String _strSrc)
    {
        if(_strSrc == null)
            return null;
        try
        {
            String s = new String(_strSrc.getBytes(), "ISO-8859-1");
            return s;
        }
        catch(Exception ex)
        {
            String s1 = _strSrc;
            return s1;
        }
    }

    public static String toUnicode(String _strSrc)
    {
        return toISO_8859(_strSrc);
    }

    public static byte[] getUTF8Bytes(String _string)
    {
        char c[] = _string.toCharArray();
        int len = c.length;
        int count = 0;
        for(int i = 0; i < len; i++)
        {
            int ch = c[i];
            if(ch <= 127)
            {
                count++;
                continue;
            }
            if(ch <= 2047)
                count += 2;
            else
                count += 3;
        }

        byte b[] = new byte[count];
        int off = 0;
        for(int i = 0; i < len; i++)
        {
            int ch = c[i];
            if(ch <= 127)
            {
                b[off++] = (byte)ch;
                continue;
            }
            if(ch <= 2047)
            {
                b[off++] = (byte)(ch >> 6 | 0xc0);
                b[off++] = (byte)(ch & 0x3f | 0x80);
            } else
            {
                b[off++] = (byte)(ch >> 12 | 0xe0);
                b[off++] = (byte)(ch >> 6 & 0x3f | 0x80);
                b[off++] = (byte)(ch & 0x3f | 0x80);
            }
        }

        return b;
    }

    public static String getUTF8String(byte b[])
    {
        return getUTF8String(b, 0, b.length);
    }

    public static String getUTF8String(byte b[], int off, int len)
    {
        int count = 0;
        int max = off + len;
        int i = off;
        do
        {
            if(i >= max)
                break;
            int c = b[i++] & 0xff;
            switch(c >> 4)
            {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
                count++;
                break;

            case 12: // '\f'
            case 13: // '\r'
                if((b[i++] & 0xc0) != 128)
                    throw new IllegalArgumentException();
                count++;
                break;

            case 14: // '\016'
                if((b[i++] & 0xc0) != 128 || (b[i++] & 0xc0) != 128)
                    throw new IllegalArgumentException();
                count++;
                break;

            case 8: // '\b'
            case 9: // '\t'
            case 10: // '\n'
            case 11: // '\013'
            default:
                throw new IllegalArgumentException();
            }
        } while(true);
        if(i != max)
            throw new IllegalArgumentException();
        char cs[] = new char[count];
        i = 0;
        do
        {
            if(off >= max)
                break;
            int c = b[off++] & 0xff;
            switch(c >> 4)
            {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
                cs[i++] = (char)c;
                break;

            case 12: // '\f'
            case 13: // '\r'
                cs[i++] = (char)((c & 0x1f) << 6 | b[off++] & 0x3f);
                break;

            case 14: // '\016'
                int t = (b[off++] & 0x3f) << 6;
                cs[i++] = (char)((c & 0xf) << 12 | t | b[off++] & 0x3f);
                break;

            case 8: // '\b'
            case 9: // '\t'
            case 10: // '\n'
            case 11: // '\013'
            default:
                throw new IllegalArgumentException();
            }
        } while(true);
        return new String(cs, 0, count);
    }

    public static String byteToHexString(byte _bytes[])
    {
        return byteToHexString(_bytes, ',');
    }

    public static String byteToHexString(byte _bytes[], char _delim)
    {
        String sRet = "";
        for(int i = 0; i < _bytes.length; i++)
        {
            if(i > 0)
                sRet = String.valueOf(String.valueOf(sRet)).concat(",");
            sRet = String.valueOf(sRet) + String.valueOf(Integer.toHexString(_bytes[i]));
        }

        return sRet;
    }

    public static String byteToString(byte _bytes[], char _delim, int _radix)
    {
        String sRet = "";
        for(int i = 0; i < _bytes.length; i++)
        {
            if(i > 0)
                sRet = String.valueOf(String.valueOf(sRet)).concat(",");
            sRet = String.valueOf(sRet) + String.valueOf(Integer.toString(_bytes[i], _radix));
        }

        return sRet;
    }

    public static String transDisplay(String _sContent)
    {
        return transDisplay(_sContent, true);
    }

    public static String transDisplay(String _sContent, boolean _bChangeBlank)
    {
        if(_sContent == null)
            return "";
        char srcBuff[] = _sContent.toCharArray();
        int nSrcLen = srcBuff.length;
        StringBuffer retBuff = new StringBuffer(nSrcLen * 2);
        for(int i = 0; i < nSrcLen; i++)
        {
            char cTemp = srcBuff[i];
            switch(cTemp)
            {
            case 32: // ' '
                retBuff.append(_bChangeBlank ? "&nbsp;" : " ");
                break;

            case 60: // '<'
                retBuff.append("&lt;");
                break;

            case 62: // '>'
                retBuff.append("&gt;");
                break;

            case 10: // '\n'
                retBuff.append("<br>");
                break;

            case 34: // '"'
                retBuff.append("&quot;");
                break;

            case 38: // '&'
                boolean bUnicode = false;
                for(int j = i + 1; j < nSrcLen && !bUnicode; j++)
                {
                    cTemp = srcBuff[j];
                    if(cTemp == '#' || cTemp == ';')
                    {
                        retBuff.append("&");
                        bUnicode = true;
                    }
                }

                if(!bUnicode)
                    retBuff.append("&amp;");
                break;

            case 9: // '\t'
                retBuff.append(_bChangeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;" : "    ");
                break;

            default:
                retBuff.append(cTemp);
                break;
            }
        }

        return retBuff.toString();
    }

    public static String transDisplay_bbs(String _sContent, String p_sQuoteColor)
    {
        return transDisplay_bbs(_sContent, p_sQuoteColor, true);
    }

    public static String transDisplay_bbs(String _sContent, String p_sQuoteColor, boolean _bChangeBlank)
    {
        if(_sContent == null)
            return "";
        boolean bIsQuote = false;
        boolean bIsNewLine = true;
        char srcBuff[] = _sContent.toCharArray();
        int nSrcLen = srcBuff.length;
        StringBuffer retBuff = new StringBuffer((int)((double)nSrcLen * 1.8D));
        for(int i = 0; i < nSrcLen; i++)
        {
            char cTemp = srcBuff[i];
            switch(cTemp)
            {
            case 58: // ':'
                if(bIsNewLine)
                {
                    bIsQuote = true;
                    retBuff.append(String.valueOf(String.valueOf((new StringBuffer("<font color=")).append(p_sQuoteColor).append(">:"))));
                } else
                {
                    retBuff.append(":");
                }
                bIsNewLine = false;
                break;

            case 32: // ' '
                retBuff.append(_bChangeBlank ? "&nbsp;" : " ");
                bIsNewLine = false;
                break;

            case 60: // '<'
                retBuff.append("&lt;");
                bIsNewLine = false;
                break;

            case 62: // '>'
                retBuff.append("&gt;");
                bIsNewLine = false;
                break;

            case 34: // '"'
                retBuff.append("&quot;");
                bIsNewLine = false;
                break;

            case 38: // '&'
                retBuff.append("&amp;");
                bIsNewLine = false;
                break;

            case 9: // '\t'
                retBuff.append(_bChangeBlank ? "&nbsp;&nbsp;&nbsp;&nbsp;" : "    ");
                bIsNewLine = false;
                break;

            case 10: // '\n'
                if(bIsQuote)
                {
                    bIsQuote = false;
                    retBuff.append("</font>");
                }
                retBuff.append("<br>");
                bIsNewLine = true;
                break;

            default:
                retBuff.append(cTemp);
                bIsNewLine = false;
                break;
            }
        }

        if(bIsQuote)
            retBuff.append("</font>");
        return retBuff.toString();
    }

    public static String transJsDisplay(String _sContent)
    {
        if(_sContent == null)
            return "";
        char srcBuff[] = _sContent.toCharArray();
        int nSrcLen = srcBuff.length;
        StringBuffer retBuff = new StringBuffer((int)((double)nSrcLen * 1.5D));
        for(int i = 0; i < nSrcLen; i++)
        {
            char cTemp = srcBuff[i];
            switch(cTemp)
            {
            case 60: // '<'
                retBuff.append("&lt;");
                break;

            case 62: // '>'
                retBuff.append("&gt;");
                break;

            case 34: // '"'
                retBuff.append("&quot;");
                break;

            default:
                retBuff.append(cTemp);
                break;
            }
        }

        return retBuff.toString();
    }

    public static String transDisplayMark(String _strSrc, char p_chrMark)
    {
        if(_strSrc == null)
            return "";
        char buff[] = new char[_strSrc.length()];
        for(int i = 0; i < buff.length; i++)
            buff[i] = p_chrMark;

        return new String(buff);
    }

    public static String filterForSQL(String _sContent)
    {
        if(_sContent == null)
            return "";
        int nLen = _sContent.trim().length();
        if(nLen == 0)
            return "";
        char srcBuff[] = _sContent.trim().toCharArray();
        StringBuffer retBuff = new StringBuffer((int)((double)nLen * 1.5D));
        for(int i = 0; i < nLen; i++)
        {
            char cTemp = srcBuff[i];
            switch(cTemp)
            {
            case 39: // '\''
                retBuff.append("''");
                break;
            /*    
            case 59: // ';'
                boolean bSkip = false;
                for(int j = i + 1; j < nLen && !bSkip; j++)
                {
                    char cTemp2 = srcBuff[j];
                    if(cTemp2 == ' ')
                        continue;
                    if(cTemp2 == '&')
                        retBuff.append(';');
                    bSkip = true;
                }

                if(!bSkip)
                    retBuff.append(';');
                break;
            */
            default:
                retBuff.append(cTemp);
                break;
            }
        }

        return retBuff.toString();
    }

    public static String filterForXML(String _sContent)
    {
        if(_sContent == null)
            return "";
        char srcBuff[] = _sContent.toCharArray();
        int nLen = srcBuff.length;
        if(nLen == 0)
            return "";
        StringBuffer retBuff = new StringBuffer((int)((double)nLen * 1.8D));
        for(int i = 0; i < nLen; i++)
        {
            char cTemp = srcBuff[i];
            switch(cTemp)
            {
            case 38: // '&'
                retBuff.append("&amp;");
                break;

            case 60: // '<'
                retBuff.append("&lt;");
                break;

            case 62: // '>'
                retBuff.append("&gt;");
                break;

            case 34: // '"'
                retBuff.append("&quot;");
                break;

            case 39: // '\''
                retBuff.append("&apos;");
                break;

            default:
                retBuff.append(cTemp);
                break;
            }
        }

        return retBuff.toString();
    }

    public static String filterForHTMLValue(String _sContent)
    {
        if(_sContent == null)
            return "";
        char srcBuff[] = _sContent.toCharArray();
        int nLen = srcBuff.length;
        if(nLen == 0)
            return "";
        StringBuffer retBuff = new StringBuffer((int)((double)nLen * 1.8D));
        for(int i = 0; i < nLen; i++)
        {
            char cTemp = srcBuff[i];
            switch(cTemp)
            {
            case 38: // '&'
                if(i + 1 < nLen)
                {
                    cTemp = srcBuff[i + 1];
                    if(cTemp == '#')
                        retBuff.append("&");
                    else
                        retBuff.append("&amp;");
                } else
                {
                    retBuff.append("&amp;");
                }
                break;

            case 60: // '<'
                retBuff.append("&lt;");
                break;

            case 62: // '>'
                retBuff.append("&gt;");
                break;

            case 34: // '"'
                retBuff.append("&quot;");
                break;

            default:
                retBuff.append(cTemp);
                break;
            }
        }

        return retBuff.toString();
    }

    public static String filterForUrl(String _sContent)
    {
        if(_sContent == null)
            return "";
        char srcBuff[] = _sContent.toCharArray();
        int nLen = srcBuff.length;
        if(nLen == 0)
            return "";
        StringBuffer retBuff = new StringBuffer((int)((double)nLen * 1.8D));
        for(int i = 0; i < nLen; i++)
        {
            char cTemp = srcBuff[i];
            switch(cTemp)
            {
            case 37: // '%'
                retBuff.append("%25");
                break;

            case 63: // '?'
                retBuff.append("%3F");
                break;

            case 35: // '#'
                retBuff.append("%23");
                break;

            case 38: // '&'
                retBuff.append("%26");
                break;

            case 32: // ' '
                retBuff.append("%20");
                break;

            default:
                retBuff.append(cTemp);
                break;
            }
        }

        return retBuff.toString();
    }

    public static String filterForJs(String _sContent)
    {
        if(_sContent == null)
            return "";
        char srcBuff[] = _sContent.toCharArray();
        int nLen = srcBuff.length;
        if(nLen == 0)
            return "";
        StringBuffer retBuff = new StringBuffer((int)((double)nLen * 1.8D));
        for(int i = 0; i < nLen; i++)
        {
            char cTemp = srcBuff[i];
            switch(cTemp)
            {
            case 34: // '"'
                retBuff.append("\\\"");
                break;

            case 92: // '\\'
                retBuff.append("\\\\");
                break;

            case 10: // '\n'
                retBuff.append("\\n");
                break;

            case 13: // '\r'
                retBuff.append("\\r");
                break;

            case 47: // '/'
                retBuff.append("\\/");
                break;

            default:
                retBuff.append(cTemp);
                break;
            }
        }

        return retBuff.toString();
    }

    public static String filterForVariableName(String _sContent)
    {
        if(_sContent == null)
            return "";
        char srcBuff[] = _sContent.toCharArray();
        int nLen = srcBuff.length;
        if(nLen == 0)
            return "";
        StringBuffer retBuff = new StringBuffer((int)((double)nLen * 1.8D));
        for(int i = 0; i < nLen; i++)
        {
            char cTemp = srcBuff[i];
            String sTemp = new String(""+cTemp);
            Pattern p = Pattern.compile("[a-zA-Z0-9_]");
        	Matcher m = p.matcher(sTemp);
       	 	boolean b = m.matches();
       	 	if(b)
       	 		retBuff.append(cTemp);
            
        }
        String s = retBuff.toString();
        
        return s.equals("") ? "v" : s;
    }

    
    public static String numberToStr(int _nValue)
    {
        return numberToStr(_nValue, 0);
    }

    public static String numberToStr(int _nValue, int _length)
    {
        return numberToStr(_nValue, _length, '0');
    }

    public static String numberToStr(int _nValue, int _length, char _chrFill)
    {
        String sValue = String.valueOf(_nValue);
        return expandStr(sValue, _length, _chrFill, true);
    }

    public static String numberToStr(long _lValue)
    {
        return numberToStr(_lValue, 0);
    }

    public static String numberToStr(long _lValue, int _length)
    {
        return numberToStr(_lValue, _length, '0');
    }

    public static String numberToStr(long _lValue, int _length, char _chrFill)
    {
        String sValue = String.valueOf(_lValue);
        return expandStr(sValue, _length, _chrFill, true);
    }

    public static String circleStr(String _strSrc)
    {
        if(_strSrc == null)
            return null;
        String sResult = "";
        int nLength = _strSrc.length();
        for(int i = nLength - 1; i >= 0; i--)
            sResult = String.valueOf(sResult) + String.valueOf(_strSrc.charAt(i));

        return sResult;
    }

    public static String truncateStr(String _string, int _maxLength)
    {
        return truncateStr(_string, _maxLength, "..");
    }

    public static String truncateStr(String _string, int _maxLength, String _sExt)
    {
        if(_string == null)
            return null;
        String sExt = "..";
        if(_sExt != null)
            sExt = _sExt;
        int nExtLen = getBytesLength(sExt);
        if(nExtLen >= _maxLength)
            return _string;
        int nMaxLen = (_maxLength - nExtLen) + 1;
        char srcBuff[] = _string.toCharArray();
        int nLen = srcBuff.length;
        StringBuffer dstBuff = new StringBuffer(nLen + 2);
        int nGet = 0;
        int i = 0;
        do
        {
            if(i >= nLen)
                break;
            char aChar = srcBuff[i];
            boolean bUnicode = false;
            int j = 0;
            if(aChar == '&')
            {
                for(j = i + 1; j < nLen && j < i + 9 && !bUnicode; j++)
                {
                    char cTemp = srcBuff[j];
                    if(cTemp != ';')
                        continue;
                    if(j == i + 5)
                    {
                        bUnicode = false;
                        j = 0;
                        break;
                    }
                    bUnicode = true;
                }

                nGet++;
            } else
            {
                nGet += aChar > '\177' ? 2 : 1;
            }
            if(nGet >= nMaxLen)
            {
                if(nGet == _maxLength && i == nLen - 1)
                {
                    dstBuff.append(aChar);
                    for(; i < j - 1; i++)
                        dstBuff.append(srcBuff[i + 1]);

                } else
                {
                    dstBuff.append(sExt);
                }
                break;
            }
            dstBuff.append(aChar);
            for(; i < j - 1; i++)
                dstBuff.append(srcBuff[i + 1]);

            i++;
        } while(true);
        return dstBuff.toString();
    }

    public static String filterForJDOM(String _string)
    {
        if(_string == null)
            return null;
        char srcBuff[] = _string.toCharArray();
        int nLen = srcBuff.length;
        StringBuffer dstBuff = new StringBuffer(nLen);
        for(int i = 0; i < nLen; i++)
        {
            char aChar = srcBuff[i];
            if(aChar >= ' ' || i != nLen - 1)
                dstBuff.append(aChar);
        }

        return dstBuff.toString();
    }

    public static int getBytesLength(String _string)
    {
        if(_string == null)
            return 0;
        char srcBuff[] = _string.toCharArray();
        int nGet = 0;
        for(int i = 0; i < srcBuff.length; i++)
        {
            char aChar = srcBuff[i];
            nGet += aChar > '\177' ? 2 : 1;
        }

        return nGet;
    }

    public static String cutStr(String _string, int _length)
    {
        return truncateStr(_string, _length);
    }

    public static String makeStr(String _str, int _length) {
    	if(_length <= 0 ) return "";
    	StringBuffer s = new StringBuffer();
    	for(int i = 0; i< _length; i++){
    		s.append(_str);
    	}
    	return s.toString();
    }
    
    
//  ==================== ͨ������ת���ʹ���ķ��� =========================

    public static String DEFAULT_LANGUAGE = "zh_CN";
    /**
     * 整型的空值。用户数据不要使用
     */
    public static int WRONG_INT =  -99999999 ;
    /**
     * 条件判断中的“等于”。用户数据不要使用
     */
    public static String EQUAL_PREFIX = ":c:e:q-c:e:q:"  ;
    /**
     * 条件判断中的“不等于”。用户数据不要使用
     */
    public static String NOT_EQUAL_PREFIX = ":c:n:e-c:n:e:"  ;
    /**
     * 条件判断中的“大于”。用户数据不要使用
     */
    public static String GREATTER_PREFIX = ":c:g:t-c:g:t:"  ;
    /**
     * 条件判断中的“小于”。用户数据不要使用
     */
    public static String LESS_PREFIX = ":c:l:s-c:l:s:"  ;
    /**
     * 条件判断中的“开始于”。用户数据不要使用
     */
    public static String START_PREFIX = ":c:s:t-c:s:t:"  ;
    /**
     * 条件判断中的“结束于”。用户数据不要使用
     */
    public static String END_PREFIX = ":c:e:d-c:e:d:"  ;
    /**
     * 条件判断中的“包含”。用户数据不要使用
     */
    public static String INCLUDE_PREFIX = ":c:i:d-c:i:d:"  ;
    /**
     * 条件判断中的“不包含”。用户数据不要使用
     */
    public static String NOT_INCLUDE_PREFIX = ":c:n:i-c:n:i:"  ;
    /**
     * 条件判断中的“不包含”。用户数据不要使用
     */
    public static String SAME_NAME = ":s:m:n-s:m:n:"  ;
    /**
     * txt文件中数据的分隔符
     */
    public static String TXT_FILE_SEPARATOR = "\t"  ;
    
    /**
     * 字节与十六进制转换过程用到的分隔符
     */
    public static String  BYTE_SPLLTER = ":" ;
    /**
     * 字节与十六进制转换过程用到的分隔符
     */
    
    /**
     * 计算百分率
     * @param myvalue 数据值
     * @param mytotal 数据总值
     * @param mywei 小数点后位数
     * @return 计算后的百分率
     */
    public static float getPercent(String myvalue,
                                    String mytotal,
                                    int  mywei) {
      int mytt = stringToInt(mytotal);
      if ( mytt < 1 ) mytt = 1;

      return getPercent(myvalue, mytt,1);
    }

    /**
     * 计算百分率
     * @param myvalue 数据值
     * @param mytotal 数据总值
     * @param mywei 小数点后位数
     * @return 计算后的百分率
     */
    public static float getPercent(String myvalue,
                                    int   mytotal,
                                    int  mywei) {
      if ( mytotal < 1 ) mytotal = 1 ;
      int myrate = stringToInt(myvalue);
      if ( myrate < 0 ) myrate = 0;
      if ( mywei < 0 ) mywei = 0;
      float myret = myrate * 100f / mytotal;
      return getRound(myret,mywei) ;
    }

    /**
     * 截去浮点数多余位数，四舍五入。
     * @param myvalue 浮点数
     * @param mywei 小数点后保留位数
     * @return 保留小数点后位数四舍五入后的浮点值
     */
    public static float getRound(float  myvalue,
                                    int  mywei) {
      float myret = myvalue;

      if ( mywei < 0 ) mywei = 0;
      int myjin = 1;
      for (int i=0; i< mywei; i++) {
        myjin = myjin * 10;
      }
      myret = myvalue * myjin;
      myret = Math.round(myvalue * myjin) * 1f / myjin ;
      return myret;

    }

    /**
     * 过滤掉重复的数据
     * @param mylist 数据集
     * @return 过滤后的数据集
     */
    public static ArrayList getUniqueList(ArrayList  mylist) {

      if (mylist == null) return null;
      ArrayList mynew = new ArrayList();
      Object myd;
      for (int i=0; i< mylist.size(); i++) {
        myd = mylist.get(i);
        if ( mynew.contains(myd)) continue;
        mynew.add(myd);
      }
      return mynew;

    }


    /**
     * 字符串转换为列表
     * @param inStr 字符串
     * @param inSplit 分割符
     * @return 转换后的列表。若异常则返回null。
     */
    public static ArrayList stringToArray(String inStr,
                                          String inSplit) {
      try {
        if ( ( inStr == null) || ( inSplit == null ) )
          return null;
        ArrayList mys = new ArrayList();
        String[] myss = inStr.split(inSplit);
        for (int i=0; i < myss.length; i++) {
          if ( "".equals(myss[i] )  ) continue;
          mys.add(myss[i]);
        }
        return mys;
      }
      catch (Exception ex) {
        return null;
      }
    }

    /**
     * 列表转换成hash数据
     * @param inNames 名表
     * @param inValues 值表
     * @return 转换后的hash数据。若异常则返回null。
     */
    public static Hashtable arraysToHash(ArrayList inNames,
                                         ArrayList inValues) {
      try {

        Hashtable myd = new Hashtable();
        for (int i=0; i < inNames.size(); i++) {
          myd.put(inNames.get(i), inValues.get(i));
        }
        return myd;
      }
      catch (Exception ex) {
        return null;
      }
    }


    /**
     * 字符串转换为名-值两列表
     * @param inStr 字符串
     * @param inSplit 分割符
     * @return 名-值两列表
     */
    public static ArrayList[] stringToArrays(String inStr,
        String inSplit) {
      try {
        ArrayList my1 = new ArrayList();
        ArrayList my2 = new ArrayList();
        String[] myss = inStr.split(inSplit);
        for (int i=0; i < myss.length; i++) {
          i++;
          if ( i >= myss.length ) break;
          if (  "".equals(myss[i-1]) || "".equals(myss[i]) )
            continue;
          my1.add(myss[i-1]);
          my2.add(myss[i]);
        }

        ArrayList[] ret = {my1,my2};
        return ret;
      }
      catch (Exception ex) {
        return null;
      }
    }


    /**
     * 字符串转换整数
     * @param inString 字符串
     * @return 转换后的整数。异常返回一个大负数。
     */
    public static int stringToInt(String inString) {

      try {
        if ( inString == null ) return WRONG_INT;
        Integer myint = new Integer( inString );

        return myint.intValue();
      }
      catch (Exception ex) {
        return WRONG_INT;
      }

    }

    /**
     * 字符串转换整数
     * @param inString 字符串
     * @return 转换后的整数。异常返回一个大负数。
     */
    public static long stringToLong(String inString) {

      try {
        Long mylong = new Long( inString );

        return mylong.longValue();
      }
      catch (NumberFormatException ex) {
        return WRONG_INT;
      }

    }

    /**
     * 整数转换为字符串
     * @param inInt 整数
     * @return 转换后的字符串
     */
    public static String intToString(int inInt) {

//      Integer myint = new Integer( inInt );
//      return myint.toString();
      return inInt + "";

    }

    /**
     * 字符串转换浮点数
     * @param inString 字符串
     * @return 转换后的浮点数。异常返回一个大负数。
     */
    public static float stringToFloat(String inString) {
      try {
        Float myf = new Float( inString );
        return myf.floatValue();
      }
      catch (NumberFormatException ex) {
        return WRONG_INT;
      }
    }

    /**
     * 浮点数转换为字符串
     * @param myfloat 浮点数
     * @return 转换后的字符串
     */
    public static String floatToString(float myfloat) {

      return myfloat + "";

    }

    /**
     * 枚举型转换为列表
     * @param inEnum 枚举数据
     * @return 转换后的列表数据。异常返回null
     */
    public static ArrayList enumToArraylist(Enumeration inEnum) {

      try {
        ArrayList mya = new ArrayList();

        while (inEnum.hasMoreElements()) {
          mya.add( inEnum.nextElement() );
        }

        return mya;
      }
      catch (Exception ex) {
        return null;
      }
    }

    /**
     * 合并列表
     * @param a1 列表数据
     * @param a2 列表数据
     * @return 合并后的列表数据
     */
    public static ArrayList mergeArray(ArrayList a1,
                                       ArrayList a2) {
      ArrayList mya = new ArrayList();
      for (int i=0; i< a1.size(); i++) {
        if ( mya.contains(a1.get(i))) continue;
        mya.add(a1.get(i));
      }
      for (int i=0; i< a2.size(); i++) {
        if ( mya.contains(a2.get(i))) continue;
        mya.add(a2.get(i));
      }
      return mya;
    }

    /**
     * 数组转换为列表
     * @param inArray 数组
     * @return 转换后的列表
     */
    public static ArrayList arrayToList(String[] inArray) {

      try {
        if (inArray == null) return null;
        ArrayList mya = new ArrayList();

        for (int i = 0; i< inArray.length; i++) {
          mya.add( inArray[i] );
        }

        return mya;
      }
      catch (Exception ex) {
        return null;
      }

    }

    /**
     * 数组转换为字符串
     * @param inArray 数组
     * @param insplit 分割符
     * @return 转换后字符串
     */
    public static String arrayToString(String[] inArray,
                                       String insplit) {

      try {
        if ( inArray == null  ) return null;
        if ( insplit == null  ) return null;
        StringBuffer mys = new StringBuffer();
        for (int i = 0; i< inArray.length-1; i++) {
          mys.append(inArray[i]);
          mys.append(insplit);
        }
        if ( inArray.length > 0 )
          mys.append(inArray[inArray.length-1]);
        return mys.toString();
      }
      catch (Exception ex) {
        return null;
      }

    }

    /**
     * 字符串转换为布尔值
     * @param inStr 字符串
     * @return 转换后的布尔值
     */
    public static boolean stringToBoolean(String inStr) {

      try {

        if ( "true".equals(inStr) ) return true;
        if ( "1".equals(inStr) ) return true;
        if ( "yes".equals(inStr) ) return true;
        return false;
      }
      catch (Exception ex) {
        return false;
      }

    }

    /**
     * 字符串转换为hash数据
     * @param inStr 字符串
     * @param inSplit 分割符
     * @return 转换后的hash数据
     */
    public static Hashtable stringToHash(String inStr,
                                         String inSplit) {
      Hashtable myd = new Hashtable();
      try {
        String[] myss = inStr.split(inSplit);
        String myk;
        for (int i=0; i < myss.length; i++) {
          i++;
          if ( i >= myss.length ) break;
          if (  "".equals(myss[i-1]) || "".equals(myss[i]) )
            continue;
          myk = myss[i-1];
          while ( myd.get(myk) != null)
            myk = myk + SAME_NAME;
          myd.put( myk,myss[i]);
        }
        return myd;
      }
      catch (Exception ex) {
        return myd;
      }
    }

    /**
     * hash数据转换为字符串
     * @param inHash hash数据
     * @param inKeys 名表
     * @param inSplit 分割符
     * @return 转换后的字符串
     */
    public static String hashToString(Hashtable inHash,
                                      ArrayList inKeys,
                                      String inSplit) {
      try {
        String myname;
        StringBuffer mystr = new StringBuffer();
        for (int j=0; j< inKeys.size(); j++) {
          myname = inKeys.get(j).toString();
          if ( inHash.get(myname) == null ) continue;
          mystr.append(myname);
          mystr.append(inSplit);
          mystr.append(inHash.get( myname ).toString());
          mystr.append(inSplit);
        }
        return mystr.toString();
      }
      catch (Exception ex) {
        return null;
      }

    }


    /**
     * hash数据转换为字符串
     * @param inHash hash数据
     * @param inSplit 分割符
     * @param mypass 口令域，不允许保存和显示
     * @return 转换后的字符串
     */
    public static String hashToString(Hashtable inHash,
                                      String inSplit,
                                      ArrayList mypass) {
      try {

        StringBuffer mystr = new StringBuffer();
        String kk,vv;
        for (Enumeration e = inHash.keys() ; e.hasMoreElements() ;) {
          kk =  e.nextElement().toString();
          vv = inHash.get(kk).toString();
          if ( ( mypass != null ) &&
               mypass.contains(kk) )
            vv = "";
          mystr.append(kk);
          mystr.append(inSplit);
          mystr.append(vv);
          mystr.append(inSplit);
        }
        return mystr.toString();
      }
      catch (Exception ex) {
        return null;
      }
    }

    /**
     * 列表转换为字符串
     * @param inList 列表
     * @param inSplit 分割符
     * @return 转换后的字符串
     */
    public static String arraylistToString(ArrayList inList,
        String inSplit) {

      try {
        if ( inList == null ) return null;
        StringBuffer str = new StringBuffer();
        int len = inList.size();
        if ( len > 0 )
          str =  new StringBuffer(inList.get(0).toString());
        for (int i = 1; i< len; i++) {
          str.append(inSplit);
          str.append(inList.get(i).toString());
        }
        return str.toString();
      }
      catch (Exception ex) {
        return null;
      }

    }

   

    /**
     * 对整型hash表进行排序
     * @param mydata 整型hash
     * @return 排序后的整型hash
     */
    public static ArrayList sortIntHash(Hashtable mydata) {
      ArrayList mymaxk = new ArrayList();
      if ( mydata == null ) return mymaxk;
      ArrayList mymax = new ArrayList();
      String mykk;
      int myv,myvv;
      boolean isadd;
      for (Enumeration e= mydata.keys(); e.hasMoreElements();) {
        mykk = e.nextElement().toString();
        myv = stringToInt(mydata.get(mykk).toString());
        if ( myv == WRONG_INT) continue;
        isadd = false;
        if ( mymax.size() == 0) {
          mymax.add(new Integer(myv));
          mymaxk.add(mykk);
          isadd = true;
        }
        for (int i=0; i< mymax.size(); i++) {
          myvv = stringToInt(mymax.get(i).toString());
          if (  myvv < myv ) {
            mymax.add(i,new Integer(myv));
            mymaxk.add(i,mykk);
            isadd = true;
            break;
          }
        }
        if ( !isadd ) {
          mymax.add(new Integer(myv));
          mymaxk.add(mykk);
        }
      }
      return mymaxk;
    }



    /**
     * 判断一个hash是否包含另一个hash
     * @param mhash 母hash
     * @param shash 子hash
     * @return 母hash是否包含子hash
     */
    public static boolean isSubHash(Hashtable mhash,
                                    Hashtable shash) {
      try {
        if ( shash == null ) return true;
        if ( mhash == null ) return false;
        String myn;
        for (Enumeration e = shash.keys(); e.hasMoreElements();) {
          myn = e.nextElement().toString();
          if  ( mhash.get(myn) == null ) return false;
          if  ( mhash.get(myn).equals(shash.get(myn)))
            continue;
          return false;
        }
        return true;
      }
      catch (Exception ex) {
        return false;
      }
    }


   

    /**
     * 获得树根
     * @param mytheme 数据
     * @return 树根
     */
    public static ArrayList getTreeRoots(Hashtable mytheme) {
      try {
        if ( mytheme == null ) return null;
        ArrayList myr =  new ArrayList();
        for ( int i = 0 ; ; i++) {
          String myid = (new Integer(i)).toString();
          if ( mytheme.get(myid) == null ) break;
          myr.add(myid);
        }
        return myr;
      }
      catch (Exception ex) {
        return null;
      }
    }

    /**
     * 获取所有子节点的序号
     * @param myids 树节点所有序号
     * @param inParent 父节点序号
     * @return 所有子节点的序号
     */
    public static ArrayList getChildsInTree(ArrayList myids, String inParent) {

      if ( myids == null ) return null;

      ArrayList mychilds = new ArrayList();

      for ( int i = 0; i < myids.size(); i++ ) {

        String myid = (String)( myids.get(i) );

        if ( ( myid.length() <= inParent.length() ) ||
             ( myid.startsWith(inParent) == false ) )
          continue;

        String mys = myid.substring( inParent.length() + 1 );

        if ( mys == null ) continue;

        if ( mys.indexOf(".") < 0 )
          mychilds.add(myid);

      }

      return mychilds;
    }

    /**
     * 字节值转换为十六进制值
     * @param myc 字节
     * @return 十六进制值
     */
    public static int byteToInt(byte myc) {
      try {
        return myc & 0XFF;
      }
      catch (Exception ex) {
        return -1;
      }
    }

    /**
     * 字节值转换为十六进制值字串
     * @param myc 字节
     * @return 十六进制值字串
     */
    public static String byteToHex(byte myc) {
      try {
        int myi = byteToInt(myc);
        if ( myi < 1 ) return null;
        return  Integer.toHexString( myi );
      }
      catch (Exception ex) {
        return null;
      }
    }

    /**
     * 十六进制值字串转换为字节
     * @param myhex 十六进制值字串
     * @return 字节
     */
    public static byte  hexToByte(String myhex) {
      try {
        return  Integer.valueOf(myhex,16).byteValue();
      }
      catch (Exception ex) {
        return Byte.parseByte("",16);
      }
    }

    /**
     * 十六进制值字串转换为字串
     * @param myhex 十六进制值字串
     * @return 字串
     */
    public static String hexToString(String myhex) {
      try {
        if ( myhex == null ) return null;
        byte[] mybb = hexToBytes(myhex);
        if ( mybb != null) {
          return new String(mybb);
        } else
          return null;
      }
      catch (Exception ex) {
        return null;
      }
    }

    /**
     * 十六进制值字串转换为字节组
     * @param myhex 十六进制值字串
     * @return 字节组
     */
    public static byte [] hexToBytes(String myhex) {
      try {
        if ( myhex == null ) return null;
        String[] myhexs = myhex.split(BYTE_SPLLTER);
        byte[] mybb = new byte[myhexs.length];
        for (int i=0; i< myhexs.length; i++) {
          mybb[i]= hexToByte(myhexs[i]);
        }
        return mybb;
      }
      catch (Exception ex) {
        return null;
      }
    }

    /**
     * 字节组转换为十六进制值字串
     * @param myb 字节组
     * @param allowError 允许中间出错
     * @return 十六进制值字串
     */
    public static String bytesToHex(byte[] myb,
                                    boolean   allowError) {
      try {
        if ( myb == null ) return null;
        StringBuffer  myout = new StringBuffer();
        String myget;
        for ( int i=0; i< myb.length; i++) {
          myget = byteToHex(myb[i]);
          if ( myget == null ) {
            if ( allowError ) continue;
            else return null;
          }
          myout.append( myget);
          myout.append( BYTE_SPLLTER);
        }
        return myout.toString();
      }
      catch (Exception ex) {
        return null;
      }
    }

    /**
     * 字串转换为十六进制值字串
     * @param mystr 字串
     * @param allowError 允许中间出错
     * @return 十六进制值字串
     */
    public static String stringToHex(String mystr,
                                     boolean   allowError) {
      return  bytesToHex(mystr.getBytes(),allowError);
    }

   
   
    /**
     * 标识语言敏感的文本
     * @param astring 文本
     * @return 文本
     */
    public static String tt(String astring) {

      return astring;

    }
    
    //计算单字符在字符串中重复的次数
    public static int countCharInStr(String _sContent, char _char){
    	if(_sContent == null)
            return 0;
        char srcBuff[] = _sContent.toCharArray();
        int nLen = srcBuff.length;
        if(nLen == 0)
            return 0;
    
        int n = 0;
        for(int i = 0; i < nLen; i++)
        {
        	char cTemp = srcBuff[i];
        	if(cTemp == _char){
        		n = n + 1;
        	}
        }
        return n;
    }
    
    
    //删除标签，指定标签组,每组中以...为间隔标记标签的首尾，去除特殊符号的放在后面，格式为“特殊符号...”
    public static final String delTag(String content)
    {
    	String tags[]={"<img...>",
    			       "<td...</td>","<...>",
    			       "<div...>","</div...>","	...","@..."," ..."
    	};
        if(content == null || content.equals(""))
            return "";
        for(int i=0;i<tags.length;i++){
        	String tag=tags[i].toUpperCase();
        	String content_A=content.toUpperCase();
        	String tagHead=tag.substring(0,tag.indexOf("..."));
        	String tagEnd=tag.substring(tag.indexOf("...")+3,tag.length());
            if(tagEnd==null||tagEnd.equals("")||tagEnd.equalsIgnoreCase("null")){
        	   tagEnd = null;
        	}
        	boolean exist=content_A.indexOf(tagHead)>=0 && (tagEnd == null || content_A.indexOf(tagEnd)>=0);
        	while(exist){
        		content = replaceTag(content,tagHead,tagEnd);
        		content_A = content.toUpperCase();
          	    exist=content_A.indexOf(tagHead)>=0 && (tagEnd == null || content_A.indexOf(tagEnd)>=0);
        	}
        }
        return content;
    }
    //在给定字符串中删除所给标签一组
    public static final String replaceTag(String str,String tagHead,String tagEnd){
    	if(str == null || str.equals(""))
            return "";
    	if(tagHead == null || tagHead.equals(""))
    		return str;
    	String replaceStr="";
    	String content_A=str.toUpperCase();
    	int i=content_A.indexOf(tagHead);
    	if(i<0){
    		return str;
    	}
    	int ii=i+tagHead.length(); 
    	int tagEndLength=0;
    	if(tagEnd!=null&&!tagEnd.equals("")&&!tagEnd.equalsIgnoreCase("null")){
    	   ii=content_A.substring(i,content_A.length()).indexOf(tagEnd)+i;//此组标签的在原字符串中的结束位置
    	   tagEndLength = tagEnd.length();
     	}
    	if(ii<=content_A.length()-1){
    		replaceStr = content_A.substring(i,ii+tagEndLength);//判断这组标签是否是最近的一组
    		if(replaceStr.indexOf(tagHead)>=0){
    			replaceStr=replaceStr.substring(replaceStr.lastIndexOf(tagHead),replaceStr.length());
    		}
        	str = StringUtil.replaceIgnoreCase(str,replaceStr,"");
    	}
    	return str;
    }
//	public static boolean isDate(String s) {
//		if (s == null || s.trim().length() == 0)
//			return false;
//
//		try {
//			DateUtil.parseDate(s);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
	
	
	/**
	 * Splits the provided text into a list, using whitespace as the separator.
	 * The separator is not included in the returned String array.
	 * 
	 * @param str
	 *            The string to parse
	 * @return An array of parsed Strings
	 * @see #split(String, String, int)
	 */
	public static String[] split(String str) {
		return split(str, null, -1);
	}

	/**
	 * Splits the provided text into a list, using whitespace as the separator.
	 * The separator is not included in the returned String array.
	 * 
	 * @param text
	 *            The string to parse
	 * @param separator
	 *            The Characters used as the delimiters.
	 * @return An array of parsed Strings
	 * @see #split(String, String, int)
	 */
	public static String[] split(String text, String separator) {
		return split(text, separator, -1);
	}

	/**
	 * Splits the provided text into a list, using whitespace as the separator.
	 * The separator is not included in the returned String array.
	 * 
	 * @param text
	 *            The string to parse
	 * @param separator
	 *            The Characters used as the delimiters.
	 * @return An array of parsed Strings
	 * @see #split(String, String, int)
	 */
	public static String[] split(String text, char separator) {
		return split(text, String.valueOf(separator));
	}

	/**
	 * Splits the provided text into a list, based on a given separator. The
	 * separator is not included in the returned String array. The maximum
	 * number of splits to perfom can be controlled. A null separator will cause
	 * parsing to be on whitespace.
	 * <p>
	 * This is useful for quickly splitting a string directly into an array of
	 * tokens, instead of an enumeration of tokens (as<code>StringTokenizer</code>
	 * does).
	 * 
	 * @param str
	 *            The string to parse.
	 * @param separator
	 *            Characters used as the delimiters. If <code>null</code>,
	 *            splits on whitespace.
	 * @param max
	 *            The maximum number of elements to include in the list. A zero
	 *            or negative value implies no limit.
	 * @return an array of parsed Strings
	 */
	public static String[] split(String str, String separator, int max) {
		StringTokenizer tok = null;
		if (separator == null) {
			tok = new StringTokenizer(str);
		} else {
			tok = new StringTokenizer(str, separator);
		}

		int listSize = tok.countTokens();
		if (max > 0 && listSize > max) {
			listSize = max;
		}

		String[] list = new String[listSize];
		int i = 0;
		int lastTokenBegin = 0;
		int lastTokenEnd = 0;

		while (tok.hasMoreTokens()) {
			if (max > 0 && i == listSize - 1) {
				String endToken = tok.nextToken();
				lastTokenBegin = str.indexOf(endToken, lastTokenEnd);
				list[i] = str.substring(lastTokenBegin);
				break;
			} else {
				list[i] = tok.nextToken();
				lastTokenBegin = str.indexOf(list[i], lastTokenEnd);
				lastTokenEnd = lastTokenBegin + list[i].length();
			}
			i++;
		}
		return list;
	}

	/**
	 * Checks whether the String a valid Java Boolean. Null and blank string
	 * will return false.
	 * 
	 * @param s
	 *            the string to check
	 * @return true if the string is a correctly Boolean
	 */
	static public boolean isBoolean(String s) {
		if (s == null || s.trim().length() == 0)
			return false;
		return Boolean.valueOf(s.trim()).booleanValue();
	}
	
	/**
	 * Unite the string arrary into one string.
	 * 
	 * @param arr
	 *            The string arrary
	 * @param sp
	 *            The split tag.
	 * @return The string after unite.
	 */
	public static String unite(String[] arr, String sp) {
		if (arr == null)
			return null;

		if (arr.length == 0)
			return "";

		int i;

		StringBuffer buff = new StringBuffer();
		for (i = 0; i < arr.length - 1; i++)
			buff = buff.append(arr[i]).append(sp);

		buff = buff.append(arr[i]);

		return buff.toString();
	}

	/**
	 * Unite the string arrary into one string with ";".
	 * 
	 * @param arr
	 *            The string arrary
	 * @returnThe string after unite.
	 */
	public static String unite(String[] arr) {
		return unite(arr, ";");
	}

	////////////////////////////////////////////////////
	/**
	 * 产生随机字符串
	 */
	public static String getRandomString(int size){// 随机字符串
	        char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q',
	                'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
	                'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
	                'Q','W','E','R','T','Y','U','I','O','P',
	                'A','S','D','F','G','H','J','K','L','Z',
	                'X','C','V','B','N','M'};
	        Random random = new Random(); // 初始化随机数产生器
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < size; i++){
	            sb.append(c[Math.abs(random.nextInt()) % c.length]);
	        }
	        return sb.toString();
	    }

	public static String intArrayToString(int[] is, String split) {
		StringBuffer ids = new StringBuffer();
		for (int i = 0; i < is.length; i++) {
			ids.append("").append(is[i]).append(split);
		}
		ids.deleteCharAt(ids.length() - 1);
		return ids.toString();
	}
	/**
	 * 判断字符串是否为空,去除收尾空格
	 * @param str
	 */
	public static boolean isBlank(String str){
		if(str ==null)
			return true;
		
		if(str.trim().length() ==0)
			return true;
		
		return false;
	}
	
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	} 
	
	/**
	 * 
	 * @author xieguoking
	 * 
	 * @param s
	 * @return  s是否为数值
	 * 
	 * 验证s是否为数值
	 *
	 */
	public static boolean isDigit(String s){			
		 if(s ==null) 			 
			 return false;
		 
		 
		 if(s.length() ==1){			 
			 return Character.isDigit(s.charAt(0));		
		 }		 
		
		 Pattern pattern =Pattern.compile("[1-9][0-9]*");		 
		 Matcher matcher =pattern.matcher(s);		
		 return matcher.matches();
		
	}
 
    ////////////////////////////////////////////////
    public static void main(String[] ars){
    	/*String s = " ";
    	ArrayList al = stringToArray(s,";");
    	String[] s1 = s.split(";");
    	System.out.println(s1.length);
    	System.out.println(al.size());
    	System.out.println("8,9".replaceAll(",9", ""));
    	System.out.println("8,9".replaceAll(",", "','"));
    	
    	
    	String ss = "hts.html";
    	int i = ss.lastIndexOf(".");
    	System.out.println("i=" + i);
    	String sss = ss.substring(i);
    	
    	System.out.println("sss=" + sss);
    	
    	System.out.println("i=" + StringUtils.stringToInt("09"));
    	*/
//    	String s = "Jacky:00-14-22-D8-C9-6C";
//    	String s1 = StringUtils.encrypt(s);
//    	String s2 = StringUtils.encodeBase64(s);
//    	String s3 = StringUtils.decodeBase64("SmFja3k6MDAtMTQtMjItRDgtQzktNkDDM=");
//    	
//    	System.out.println("S1=" + s1);
//    	System.out.println("S2=" + s2);
//    	System.out.println("S3=" + s3);
//    	System.out.println(PropertiesFileUtils.getProperty("Name"));
    	
    	String s = StringUtil.getCurDateTime();
    	System.out.println("S=" + s);
    	try {
			System.out.println("S=" + StringUtil.getHour(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    

}
