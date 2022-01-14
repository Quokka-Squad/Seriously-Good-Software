package jerry;

import java.util.HashSet;
import java.util.Set;

public class ContainerBox {
	private Set<Container> containerList = new HashSet<>();

	public void addContainer(Container container) {
		containerList.add(container);
	}

	public void addContainerBox(ContainerBox containerBox) {
		this.containerList.addAll(containerBox.getContainerSet());
	}

	public Set<Container> getContainerSet() {
		return containerList;
	}

	public void setContainerList(Set<Container> containerList) {
		this.containerList = containerList;
	}
}
