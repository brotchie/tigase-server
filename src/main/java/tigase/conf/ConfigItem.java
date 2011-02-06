/*
 * Tigase Jabber/XMPP Server
 * Copyright (C) 2004-2008 "Artur Hefczyc" <artur.hefczyc@tigase.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 *
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */

package tigase.conf;

//~--- non-JDK imports --------------------------------------------------------

import tigase.db.comp.RepositoryItemAbstract;

import tigase.server.Command;
import tigase.server.Packet;

import tigase.util.DataTypes;

import tigase.xml.Element;

//~--- JDK imports ------------------------------------------------------------

import java.util.logging.Logger;

//~--- classes ----------------------------------------------------------------

/**
 * Created: Dec 10, 2009 2:40:26 PM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class ConfigItem extends RepositoryItemAbstract {

	/**
	 * Private logger for class instance. 
	 */
	private static final Logger log = Logger.getLogger(ConfigItem.class.getName());

	/** Field description */
	public static final String REPO_ITEM_ELEM_NAME = "prop";

	/** Field description */
	public static final String CLUSTER_NODE_ATTR = "cluster-node";

	/** Field description */
	public static final String COMPONENT_NAME_ATTR = "comp-name";

	/** Field description */
	public static final String NODE_NAME_ATTR = "node-name";

	/** Field description */
	public static final String KEY_NAME_ATTR = "key-name";

	/** Field description */
	public static final String VALUE_ATTR = "value";

	/** Field description */
	public static final String FLAG_ATTR = "flag";

	/** Field description */
	public static final String VALUE_TYPE_ATTR = "value-type";

	// public static final String CLUSTER_NODE_LABEL = "Cluster node";

	/** Field description */
	public static final String COMPONENT_NAME_LABEL = "Component name";

	/** Field description */
	public static final String NODE_NAME_LABEL = "Property node name";

	/** Field description */
	public static final String KEY_NAME_LABEL = "Property key name";

	/** Field description */
	public static final String VALUE_LABEL = "Propety value";

	//~--- constant enums -------------------------------------------------------

	/**
	 * Enum description
	 *
	 */
	public enum FLAGS { INITIAL, DEFAULT, UPDATED; }

	//~--- fields ---------------------------------------------------------------

	private String clusterNode = null;
	private String compName = null;
	private String keyName = null;
	private long lastModificationTime = -1;
	private String nodeName = null;
	private Object value = null;
	private FLAGS flag = FLAGS.DEFAULT;

	//~--- methods --------------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @param packet
	 */
	@Override
	public void addCommandFields(Packet packet) {

//  Command.addFieldValue(packet, CLUSTER_NODE_LABEL,
//      (clusterNode != null ? clusterNode : ""));
		Command.addFieldValue(packet, COMPONENT_NAME_LABEL, ((compName != null) ? compName : ""));
		Command.addFieldValue(packet, NODE_NAME_LABEL, ((nodeName != null) ? nodeName : ""));
		Command.addFieldValue(packet, KEY_NAME_LABEL, ((keyName != null) ? keyName : ""));

		String value_label = VALUE_LABEL;
		String value_str = "";

		if (value != null) {
			value_str = DataTypes.valueToString(value);
			value_label += " [" + DataTypes.getTypeId(value) + "]";
		}

		Command.addFieldValue(packet, value_label, value_str);
		super.addCommandFields(packet);
	}

	//~--- get methods ----------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public String getClusterNode() {
		return clusterNode;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * Returns a configuration property key which is constructed in a following way:
	 * <code>
	 * nodeName + "/" + keyName
	 * </code>
	 * @return
	 */
	public String getConfigKey() {
		return ((nodeName != null) ? nodeName + "/" : "") + keyName;
	}

	/**
	 * Returns a configuration property value.
	 * @return
	 */
	public Object getConfigVal() {
		return value;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public String getConfigValToString() {
		return (value == null) ? null : DataTypes.valueToString(value);
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	@Override
	public String getElemName() {
		return REPO_ITEM_ELEM_NAME;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public FLAGS getFlag() {
		return flag;
	}

	/**
	 * Returns ConfigItem key which is constructed in a following way:
	 * <code>
	 * compName + "/" + nodeName + "/" + keyName
	 * </code>
	 * @return
	 */
	@Override
	public String getKey() {
		return ((compName != null) ? compName + "/" : "") + ((nodeName != null) ? nodeName + "/" : "")
				+ keyName;
	}

	/**
	 * Returns a property key which is constructed in a following way:
	 * <code>
	 * keyName
	 * </code>
	 * @return
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	public String getNodeName() {
		return nodeName;
	}

	//~--- methods --------------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @param packet
	 */
	@Override
	public void initFromCommand(Packet packet) {
		super.initFromCommand(packet);

//  String tmp = Command.getFieldValue(packet, CLUSTER_NODE_LABEL);
//  if (tmp != null && !tmp.isEmpty()) {
//    clusterNode = tmp;
//  }
		String tmp = Command.getFieldValue(packet, COMPONENT_NAME_LABEL);

		if ((tmp != null) &&!tmp.isEmpty()) {
			compName = tmp;
		}

		tmp = Command.getFieldValue(packet, NODE_NAME_LABEL);

		if ((tmp != null) &&!tmp.isEmpty()) {
			nodeName = tmp;
		}

		tmp = Command.getFieldValue(packet, KEY_NAME_LABEL);

		if ((tmp != null) &&!tmp.isEmpty()) {
			keyName = tmp;
		}

		String value_label = VALUE_LABEL;

		if (value != null) {
			value_label += " [" + DataTypes.getTypeId(value) + "]";
		}

		tmp = Command.getFieldValue(packet, value_label);

		if ((tmp != null) &&!tmp.isEmpty()) {
			if (value != null) {
				char t = DataTypes.getTypeId(value);

				value = DataTypes.decodeValueType(t, tmp);
			} else {
				value = tmp;
			}
		}
	}

	/**
	 * Method description
	 *
	 *
	 * @param elem
	 */
	@Override
	public void initFromElement(Element elem) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Method description
	 *
	 *
	 * @param propString
	 */
	@Override
	public void initFromPropertyString(String propString) {
		int idx_eq = propString.indexOf('=');
		String[] prop = propString.split("=");

//  String key = prop[0].trim();
//  Object val = prop[1];
		String key = propString.substring(0, idx_eq);
		Object val = propString.substring(idx_eq + 1);

		if (key.matches(".*\\[[LISBlisb]\\]$")) {
			char c = key.charAt(key.length() - 2);

			key = key.substring(0, key.length() - 3);
			val = DataTypes.decodeValueType(c, prop[1]);
		}

		int idx1 = key.indexOf("/");

		if (idx1 > 0) {
			String compNameMeth = key.substring(0, idx1);
			int idx2 = key.lastIndexOf("/");
			String nodeNameMeth = null;
			String keyNameMeth = key.substring(idx2 + 1);

			if (idx1 != idx2) {
				nodeNameMeth = key.substring(idx1 + 1, idx2);
			}

			set(compNameMeth, nodeNameMeth, keyNameMeth, val);
		} else {
			throw new IllegalArgumentException("You have to provide a key with at least"
					+ " 'component_name/key_name': " + key);
		}
	}

	//~--- get methods ----------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @param comp
	 * @param node
	 * @param key
	 *
	 * @return
	 */
	public boolean isCompNodeKey(String comp, String node, String key) {
		return isComponent(comp) && isNode(node) && isKey(key);
	}

	/**
	 * Checks if the given component name is equal to this item compName.
	 * @param comp
	 * @return
	 */
	public boolean isComponent(String comp) {
		if (compName != comp) {
			return (compName != null) ? compName.equals(comp) : false;
		}

		return true;
	}

	/**
	 * Checks if the given key is equal to this item keyName.
	 * @param key
	 * @return
	 */
	public boolean isKey(String key) {
		if (keyName != key) {
			return (keyName != null) ? keyName.equals(key) : false;
		}

		return true;
	}

	/**
	 * Checks if the given node is equal to this item nodeName
	 * @param node
	 * @return
	 */
	public boolean isNode(String node) {
		if (nodeName != node) {

			// At least one is not null
			return (nodeName != null) ? nodeName.equals(node) : false;
		}

		return true;
	}

	/**
	 * Checks if the given node and key are equal to this item nodeName and keyName.
	 * This method call works the same way as following statement:
	 * <code>
	 * isNode(node) && isKey(key)
	 * </code>
	 * @param node
	 * @param key
	 * @return
	 */
	public boolean isNodeKey(String node, String key) {
		return isNode(node) && isKey(key);
	}

	//~--- set methods ----------------------------------------------------------

	/**
	 * Method description
	 *
	 *
	 * @param clusterNode_m
	 * @param compName_m
	 * @param nodeName_m
	 * @param key_m
	 * @param value_str_m
	 * @param val_type_m
	 * @param flag_str_m
	 */
	public void set(String clusterNode_m, String compName_m, String nodeName_m, String key_m,
			String value_str_m, char val_type_m, String flag_str_m) {
		Object value_m = DataTypes.decodeValueType(val_type_m, value_str_m);
		FLAGS flag_m = FLAGS.DEFAULT;

		try {
			flag_m = FLAGS.valueOf(flag_str_m);
		} catch (Exception e) {
			log.warning("Incorrect config item flag: " + flag_str_m + ", setting DEFAULT.");
			flag_m = FLAGS.DEFAULT;
		}

		set(clusterNode_m, compName_m, nodeName_m, key_m, value_m, flag_m);
	}

	/**
	 * Method description
	 *
	 *
	 * @param clusterNode_m
	 * @param compName_m
	 * @param nodeName_m
	 * @param key_m
	 * @param value_m
	 * @param flag_m
	 */
	public void set(String clusterNode_m, String compName_m, String nodeName_m, String key_m,
			Object value_m, FLAGS flag_m) {
		if (clusterNode_m != null) {
			this.clusterNode = clusterNode_m;
		}

		if (compName_m != null) {
			this.compName = compName_m;
		}

		if (nodeName_m != null) {
			this.nodeName = nodeName_m;
		}

		if (key_m != null) {
			this.keyName = key_m;
		}

		if (value_m != null) {
			this.value = value_m;
		}

		if (flag_m != null) {
			this.flag = flag_m;
		}

		lastModificationTime = System.currentTimeMillis();
	}

	/**
	 * Method description
	 *
	 *
	 * @param compName_m
	 * @param nodeName_m
	 * @param key_m
	 * @param value_str_m
	 * @param val_type_m
	 * @param flag_str_m
	 */
	public void set(String compName_m, String nodeName_m, String key_m, String value_str_m,
			char val_type_m, String flag_str_m) {
		set(null, compName_m, nodeName_m, key_m, value_str_m, val_type_m, flag_str_m);
	}

	/**
	 * Method description
	 *
	 *
	 * @param compName
	 * @param nodeName
	 * @param key
	 * @param value
	 */
	public void set(String compName, String nodeName, String key, Object value) {
		set(null, compName, nodeName, key, value, null);
	}

	/**
	 * Method description
	 *
	 *
	 * @param clusterNode
	 * @param compName
	 * @param nodeName
	 * @param key
	 * @param value
	 */
	public void set(String clusterNode, String compName, String nodeName, String key, Object value) {
		set(clusterNode, compName, nodeName, key, value, null);
	}

	/**
	 * Method description
	 *
	 *
	 * @param clusterNode
	 * @param compName
	 * @param nodeKey
	 * @param value
	 */
	public void setNodeKey(String clusterNode, String compName, String nodeKey, Object value) {
		int key_idx = nodeKey.lastIndexOf("/");
		String method_key = nodeKey;
		String method_node = null;

		if (key_idx >= 0) {
			method_key = nodeKey.substring(key_idx + 1);
			method_node = nodeKey.substring(0, key_idx);
		}

		set(clusterNode, compName, method_node, method_key, value);
	}

	//~--- methods --------------------------------------------------------------

//public static final String REPO_ITEM_ELEM_NAME = "prop";
//public static final String CLUSTER_NODE_ATTR = "cluster-node";
//public static final String COMPONENT_NAME_ATTR = "comp-name";
//public static final String NODE_NAME_ATTR = "node-name";
//public static final String KEY_NAME_ATTR = "key-name";
//public static final String VALUE_ATTR = "value";
//public static final String FLAG_ATTR = "flag";
//public static final String VALUE_TYPE_ATTR = "value-type";
//
//private String clusterNode = null;
//private String compName = null;
//private String nodeName = null;
//private String keyName = null;
//private Object value = null;
//private FLAGS flag = FLAGS.DEFAULT;

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	@Override
	public Element toElement() {
		Element elem = super.toElement();

		if (clusterNode != null) {
			elem.addAttribute(CLUSTER_NODE_ATTR, clusterNode);
		}

		elem.addAttribute(COMPONENT_NAME_ATTR, compName);

		if (nodeName != null) {
			elem.addAttribute(NODE_NAME_ATTR, nodeName);
		}

		elem.addAttribute(KEY_NAME_ATTR, keyName);
		elem.addAttribute(VALUE_ATTR, DataTypes.valueToString(value));
		elem.addAttribute(VALUE_TYPE_ATTR, "" + DataTypes.getTypeId(value));
		elem.addAttribute(FLAG_ATTR, flag.name());

		return elem;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	@Override
	public String toPropertyString() {
		char t = DataTypes.getTypeId(value);
		String result = getKey() + "[" + t + "]=";
		String varr = DataTypes.valueToString(value);

		result += varr;

		return result;
	}

	/**
	 * Method description
	 *
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return compName + "/" + nodeName + "/" + keyName + "=" + value;
	}
}


//~ Formatted in Sun Code Convention


//~ Formatted by Jindent --- http://www.jindent.com
