package nl.sogyo.webserver;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest implements Request{
	
	private HttpMethod httpMethod;
	private String resourcePath;
	private List<HeaderParameter> headerParameters;
	private List<Parameter> parameters;
	
	public HttpRequest() {
		headerParameters = new ArrayList<HeaderParameter>();
		parameters = new ArrayList<Parameter>();
	}
	
	public void parseLine(String line) {
		if (lineIsHeaderParameter(line)) {
			String name = getHeaderParameterNameFromLine(line);
			String value = getHeaderParameterValueFromLine(line);
			headerParameters.add(new HeaderParameter(name, value));
		} else {
			httpMethod = getHttpMethodFromLine(line);
			resourcePath = getResourcePathFromLine(line);
		}
	}
	
    public HttpMethod getHTTPMethod() {
    	return httpMethod;
    }
    
    public String getResourcePath() {
    	return resourcePath;
    }
    
    public void addHeaderParameter(HeaderParameter headerParameter) {
    	headerParameters.add(headerParameter);
    }
    
    public List<String> getHeaderParameterNames() {
    	List<String> headerParameterNames = new ArrayList<String>();
    	for (HeaderParameter headerParameter : headerParameters) {
    		headerParameterNames.add(headerParameter.getName());
    	}
    	return headerParameterNames;
    }
    
    public String getHeaderParameterValue(String name) {
    	for (HeaderParameter headerParameter : headerParameters) {
    		if (name.equals(headerParameter.getName())) {
    			return headerParameter.getValue();
    		}
    	}
    	return "";
    }
    
    public List<String> getParameterNames() {
    	List<String> parameterNames = new ArrayList<String>();
    	for (Parameter parameter : parameters) {
    		parameterNames.add(parameter.getName());
    	}
    	return parameterNames;
    }
    
    public String getParameterValue(String name) {
    	for (Parameter parameter : parameters) {
    		if (name.equals(parameter.getName())) {
    			return parameter.getValue();
    		}
    	}
    	return "";
    }
    
    private boolean lineIsHeaderParameter(String line) {
    	if (line.contains("GET") || line.contains("POST")) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    private HttpMethod getHttpMethodFromLine(String line) {
    	if (line.contains("GET")) {
    		return HttpMethod.GET;
    	} else {
    		return HttpMethod.POST;
    	}
    }
    
    private String getResourcePathFromLine(String line) {
    	String[] lineParts = line.split("\\s+");
    	String resourcePath;
    	if (line.contains("?")) {
    		resourcePath = lineParts[1].substring(0, lineParts[1].indexOf("?"));
    		parseParametersFromLine(lineParts[1]);
    	} else {
    		resourcePath = lineParts[1];
    	}
    	return resourcePath;
    }
    
    private String getHeaderParameterNameFromLine(String line) {
    	return line.substring(0, line.indexOf(":"));
    }
    
    private String getHeaderParameterValueFromLine(String line) {
    	return line.substring(line.indexOf(":") + 2);
    }
    
    private Parameter parseStringToParameter(String parameterString) {
    	String[] parameterNameAndValue = parameterString.split("=");
    	return new Parameter(parameterNameAndValue[0], parameterNameAndValue[1]);
    }
    
    private void parseParametersFromLine(String line) {
    	String[] parameterStrings = line.substring(line.indexOf("?") + 1).split("&");
		for (String parameterString : parameterStrings) {
			parameters.add(parseStringToParameter(parameterString));
		}
    }
}
