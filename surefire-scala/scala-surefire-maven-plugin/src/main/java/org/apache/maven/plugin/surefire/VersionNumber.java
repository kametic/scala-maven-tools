package org.apache.maven.plugin.surefire;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Stole^H^H^H^H copied from the scala-maven-plugin
class VersionNumber /*implements Serializable*/ implements Comparable<VersionNumber>{
    private static final Pattern _regexp = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?([-\\.].+)?");

    public int major;
    public int minor;
    public int bugfix;
    public String modifier;

    public VersionNumber(){
        major = 0;
        minor = 0;
        bugfix = 0;
    }

    public VersionNumber(String s) {
        Matcher match = _regexp.matcher(s);
        if (!match.find()) {
            throw new IllegalArgumentException("invalid versionNumber : major.minor(.bugfix)(modifier) :" + s);
        }
        major = Integer.parseInt(match.group(1));
        minor = Integer.parseInt(match.group(2));
        if ((match.group(3) != null) && (match.group(3).length() > 1)){
            bugfix = Integer.parseInt(match.group(3).substring(1));
        }
        if ((match.group(4) != null) && (match.group(4).length() > 1)){
            modifier = match.group(4);
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(major)
                .append('.')
                .append(minor)
                .append('.')
                .append(bugfix)
        ;
        if ((modifier != null) && (modifier.length() > 0)){
            str.append(modifier);
        }
        return str.toString();
    }

    /**
     * Doesn't compare modifier
     */
    @Override
    public int compareTo(VersionNumber o) {
        int back = 0;
        if ((back == 0) && (major > o.major)) {
            back = 1;
        }
        if ((back == 0) && (major < o.major)) {
            back = -1;
        }
        if ((back == 0) && (minor > o.minor)) {
            back = 1;
        }
        if ((back == 0) && (minor < o.minor)) {
            back = -1;
        }
        if ((back == 0) && (bugfix > o.bugfix)) {
            back = 1;
        }
        if ((back == 0) && (bugfix < o.bugfix)) {
            back = -1;
        }
        return back;
    }

    public boolean isZero() {
        return (major == 0) && (minor == 0) && (bugfix == 0);
    }


}