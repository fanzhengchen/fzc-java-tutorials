


define pack_module
mvn --projects $(1) clean package
endef

define assemble_module
mvn --projects $(1) assembly:assembly
endef

package-netty-echo-demo: clean-install
	$(call pack_module, netty-echo-demo)


assemble-netty-echo-demo: package-netty-echo-demo
	@echo 'assemble netty-echo-demo'
	$(call assemble_module, netty-echo-demo)


clean-install:
	mvn clean install