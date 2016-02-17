/*
 *     openKB4BMS is an open source knowledge base (KB) acting as a
 *     building management application.
 *
 *     Copyright (C) 2016
 *     Institute of Computer Aided Automation, Automation Systems Group, TU Wien.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package at.ac.tuwien.auto.sewoa.obix.data;

import java.util.HashMap;

public class ObixWatch {

    private String name;
    private String href;
    private String is;
    private ObixReltime reltime;
    private HashMap<String, ObixOperation> operations = new HashMap<String, ObixOperation>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }

    public ObixReltime getReltime() {
        return reltime;
    }

    public void setReltime(ObixReltime reltime) {
        this.reltime = reltime;
    }

    public HashMap<String, ObixOperation> getOperations() {
        return operations;
    }

    public void setOperations(HashMap<String, ObixOperation> operations) {
        this.operations = operations;
    }

    public void addOperation(ObixOperation operation){
        operations.put(operation.getName(), operation);
    }
}
