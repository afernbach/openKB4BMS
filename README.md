# openKB4BMS
openKB4BMS is an open source knowledge base (KB) acting as a building management application with Apache Jena as its core. openKB4BMS sits on top of an integration layer established by oBIX or OPC UA. Alternatively, it can directly connect to IoT devices via a Webservice interface. A Web ontology language (OWL) framework is used for representing the static information about a building and its technical equipment (geometry, building physics, network topologies and devices) as well as the current dynamic state. This includes temperature and humidity values, presence states and blind positions but also the state of building automation systems. This way, openKB4BMS represents a holistic knowledge base of a building and its components. Via a SPARQL interface, semantic queries on a building management system can be executed. Examples for possible queries to openKB4BMS include but are clearly not limited to:

+ Are every switching and dimming actuators of a distinct floor in “off” state?

+ Which lamps in a building have exceeded a distinct operating time?

+ Which lamps in a building are broken?

+ How many rooms of a building are occupied?

+ Which unoccupied rooms at the southern or western front of the building have their shutters in “down” position?