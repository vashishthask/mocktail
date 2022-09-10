package in.malonus.maven.plugin.mocktail;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

public abstract class AjcProperties extends AbstractMojo {
    /**
     * The maven project.
     * 
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    protected MavenProject project;

    /**
     * The basedir of the project.
     * 
     * @parameter expression="${basedir}"
     * @required
     * @readonly
     */
    protected File basedir;
    /**
     * The source directory for the aspects
     * 
     * @parameter default-value="src/main/aspect"
     */
    protected String aspectDirectory = "src/main/aspect";

    /**
     * The source directory for the test aspects
     * 
     * @parameter default-value="src/test/aspect"
     */
    protected String testAspectDirectory = "src/test/aspect";

    /**
     * List of ant-style patterns used to specify the aspects that should be
     * included when compiling. When none specified all .java and .aj files in
     * the project source directories, or directories spesified by the
     * ajdtDefFile property are included.
     * 
     * @parameter
     */
    protected String[] includes;

    /**
     * List of ant-style patterns used to specify the aspects that should be
     * excluded when compiling. When none specified all .java and .aj files in
     * the project source directories, or directories spesified by the
     * ajdtDefFile property are included.
     * 
     * @parameter
     */
    protected String[] excludes;

    /**
     * Where to find the ajdt build definition file. <i>If set this will
     * override the use of project sourcedirs</i>.
     * 
     * @parameter
     */
    protected String ajdtBuildDefFile;

    /**
     * Generate aop.xml file for load-time weaving with default
     * name.(/META-INF/aop.xml)
     * 
     * @parameter
     */
    protected boolean outxml;

    /**
     * Generate aop.xml file for load-time weaving with custom name.
     * 
     * @parameter
     */
    protected String outxmlfile;

    /**
     * Generate .ajesym symbol files for emacs support
     * 
     * @parameter
     */
    protected boolean emacssym;

    /**
     * Set default level for messages about potential programming mistakes in
     * crosscutting code. {level} may be ignore, warning, or error. This
     * overrides entries in org/aspectj/weaver/XlintDefault.properties from
     * aspectjtools.jar.
     * 
     * @parameter
     */
    protected String Xlint;

    /**
     * Enables the compiler to support hasmethod(method_pattern) and
     * hasfield(field_pattern) type patterns, but only within declare
     * statements.
     * 
     * It's experimental and undocumented because it may change, and because it
     * doesn't yet take into account ITDs.
     * 
     * @parameter
     * @since 1.3
     */
    protected boolean XhasMember;

    /**
     * Specify classfile target setting (1.1 to 1.6) default is 1.2
     * 
     * @parameter default-value="${project.build.java.target}"
     */
    protected String target;

    /**
     * Toggle assertions (1.3, 1.4, or 1.6 - default is 1.4). When using -source
     * 1.3, an assert() statement valid under Java 1.4 will result in a compiler
     * error. When using -source 1.4, treat assert as a keyword and implement
     * assertions according to the 1.4 language spec. When using -source 1.5 or
     * higher, Java 5 language features are permitted.
     * 
     * @parameter default-value="${project.build.java.target}"
     */
    protected String source;

    /**
     * Specify compiler compliance setting (1.3 to 1.6) default is 1.4
     * 
     * @parameter
     */
    protected String complianceLevel;

    /**
     * Toggle warningmessages on deprecations
     * 
     * @parameter
     */
    protected boolean deprecation;

    /**
     * Emit no errors for unresolved imports;
     * 
     * @parameter
     */
    protected boolean noImportError;

    /**
     * Keep compiling after error, dumping class files with problem methods
     * 
     * @parameter
     */
    protected boolean proceedOnError;

    /**
     * Preserve all local variables during code generation (to facilitate
     * debugging).
     * 
     * @parameter
     */
    protected boolean preserveAllLocals;

    /**
     * Compute reference information.
     * 
     * @parameter
     */
    protected boolean referenceInfo;

    /**
     * Specify default source encoding format.
     * 
     * @parameter expression="${project.build.sourceEncoding}"
     */
    protected String encoding;

    /**
     * Emit messages about accessed/processed compilation units
     * 
     * @parameter
     */
    protected boolean verbose;

    /**
     * Emit messages about weaving
     * 
     * @parameter
     */
    protected boolean showWeaveInfo;

    /**
     * Repeat compilation process N times (typically to do performance
     * analysis).
     * 
     * @parameter
     */
    protected int repeat;

    /**
     * (Experimental) runs weaver in reweavable mode which causes it to create
     * woven classes that can be rewoven, subject to the restriction that on
     * attempting a reweave all the types that advised the woven type must be
     * accessible.
     * 
     * @parameter
     */
    protected boolean Xreweavable;

    /**
     * (Experimental) do not inline around advice
     * 
     * @parameter
     */
    protected boolean XnoInline;

    /**
     * (Experimental) Normally it is an error to declare aspects Serializable.
     * This option removes that restriction.
     * 
     * @parameter
     */
    protected boolean XserializableAspects;

    /**
     * Causes the compiler to calculate and add the SerialVersionUID field to
     * any type implementing Serializable that is affected by an aspect. The
     * field is calculated based on the class before weaving has taken place.
     * 
     * @parameter
     */
    protected boolean XaddSerialVersionUID;

    /**
     * Override location of VM's bootclasspath for purposes of evaluating types
     * when compiling. Path is a single argument containing a list of paths to
     * zip files or directories, delimited by the platform-specific path
     * delimiter.
     * 
     * @parameter
     */
    protected String bootclasspath;

    /**
     * Emit warnings for any instances of the comma-delimited list of
     * questionable code (eg 'unusedLocals,deprecation'): see
     * http://www.eclipse.org/aspectj/doc/released/devguide/ajc-ref.html#ajc for
     * available settings
     * 
     * @parameter
     */
    protected String warn;

    /**
     * The filename to store build configuration in. This file will be placed in
     * the project build output directory, and will contain all the arguments
     * passed to the compiler in the last run, and also all the filenames
     * included in the build. Aspects as well as java files.
     * 
     * @parameter default-value="builddef.lst"
     */
    protected String argumentFileName = "builddef.lst";

    /**
     * Forces re-compilation, regardless of whether the compiler arguments or
     * the sources have changed.
     * 
     * @parameter
     */
    protected boolean forceAjcCompile;
}
