package jerry;

import java.util.HashSet;
import java.util.Set;

public class ContainerBox {
	private Set<Container> containerSet = new HashSet<>();

	public void addContainer(Container container) {
		containerSet.add(container);
	}

	public void addContainerBox(ContainerBox containerBox) {
		this.containerSet.addAll(containerBox.getContainerSet());
	}

	public Set<Container> getContainerSet() {
		return containerSet;
	}

	public void setContainerSet(Set<Container> containerSet) {
		this.containerSet = containerSet;
	}
}
