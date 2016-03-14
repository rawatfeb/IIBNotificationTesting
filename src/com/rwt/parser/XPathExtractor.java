package com.rwt.parser;

import static com.rwt.constant.Constants.CUS;
import static com.rwt.constant.Constants.CUS_INFO;
import static com.rwt.constant.Constants.CUS_INFO_NAMESPACE;
import static com.rwt.constant.Constants.CUS_NAMESPACE;

import java.io.InputStream;
import java.util.List;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.XPathContext;

public class XPathExtractor {
	private XPathContext xc;
	private Element m_MDMPublish;

	public void init(InputStream ins) throws Exception {
		Builder builder = new Builder();
		Document doc = builder.build(ins);
		m_MDMPublish = doc.getRootElement();
		xc = XPathContext.makeNamespaceContext(m_MDMPublish);
		xc.addNamespace(CUS_INFO, CUS_INFO_NAMESPACE);
		xc.addNamespace(CUS, CUS_NAMESPACE);
	}

	private String getValueByXpath(String xPath) {
		Nodes result = null;
		result = m_MDMPublish.query(xPath, xc);
		// need enhancement here
		/*
		 * for (int i = 0; i < result.size(); i++) { System.out.print(" "
		 * +result.get(i).getValue()); }
		 */
		if (result.size() != 0) {
			return result.get(0).getValue();
		} else
			return "";
	}

	public boolean matchValuesByXpath(List<String> xPaths, List<String> expectedValues) {
		boolean isDocumentIDMatched = false;
		if (xPaths.size() > 0) {
			isDocumentIDMatched = matchValueByXpath(xPaths.get(0), expectedValues.get(0));
			if (isDocumentIDMatched) {
				for (int j = 1; j < xPaths.size(); j++) {
					Nodes result = m_MDMPublish.query(xPaths.get(j), xc);
					if (null != result && result.size() != 0) {
						// System.out.println(result.get(0).getValue());
						isDocumentIDMatched = isDocumentIDMatched
								& result.get(0).getValue().equals(expectedValues.get(j));
					} else {
						isDocumentIDMatched = false;
					}
				}
			}
		}
		return isDocumentIDMatched;
	}

	public boolean matchValueByXpath(String xPath, String Value) {
		boolean isMatched = false;
		isMatched = getValueByXpath(xPath).equals(Value);
		return isMatched;
	}
}
