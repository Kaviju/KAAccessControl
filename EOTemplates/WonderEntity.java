#if ($entity.packageName)
package $entity.packageName;

#end
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@SuppressWarnings("serial")
public#if (${entity.abstractEntity}) abstract#end class ${entity.classNameWithoutPackage} extends ${entity.prefixClassNameWithOptionalPackage} {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(${entity.classNameWithoutPackage}.class);
}
