package com.locker.configuration;

/**
 * Created by randyr on 2/17/16.
 */

/*@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.locker")
@PropertySource("classpath:application.properties")
public class Initializer extends
        AbstractAnnotationConfigDispatcherServletInitializer implements
        WebApplicationInitializer {

    /**
     * @param servletContext
     * @throws ServletException
     */
    /*@Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        //enable annotation config
        AnnotationConfigWebApplicationContext ctx
                = new AnnotationConfigWebApplicationContext();
        //register the anno config
        ctx.register(MvcConfig.class);
        servletContext.addListener(new ContextLoaderListener(ctx));

        ctx.setServletContext(servletContext);

        Dynamic servlet = servletContext.addServlet("dispatcher",
                new DispatcherServlet(ctx));
        //add mapping
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
    }

    /**
     * Load the root config class
     *
     * @return
     */
    /*@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MvcConfig.class};
    }

    /**
     * @return
     */
    /*@Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    /**
     * @return
     */
    /*@Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }*/

    /**
     * @param registration
     */
    /*@Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
    }*/

    /**
     * This function is for uploading files and storing them temp on the hdd
     *
     * @return
     */
    /*private MultipartConfigElement getMultipartConfigElement() {

        File dir = new File(LOCATION + File.separator
                + "FancyCodeImages" + File.separator + "Temp");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        MultipartConfigElement multipartConfigElement
                = new MultipartConfigElement(dir.getAbsolutePath()
                + File.separator,
                MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
        return multipartConfigElement;
    }

    // Temporary location where files will be stored
    private static final String LOCATION = System.getProperty("user.home");

    // 6MB : Max file size.
    private static final long MAX_FILE_SIZE = 6242880;

    // Beyond that size spring will throw exception.
    // 20MB : Total request size containing Multi part.
    private static final long MAX_REQUEST_SIZE = 200971520;

    // Size threshold after which files will be written to disk
    private static final int FILE_SIZE_THRESHOLD = 0;

}*/