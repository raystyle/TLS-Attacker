/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.workflow.action;

import de.rub.nds.tlsattacker.tls.exceptions.WorkflowExecutionException;
import de.rub.nds.tlsattacker.tls.workflow.TlsContext;
import de.rub.nds.tlsattacker.tls.workflow.action.executor.ActionExecutor;
import java.io.IOException;

/**
 * 
 * @author Robert Merget - robert.merget@rub.de
 */
public class ChangeClientRandomAction extends TLSAction {
    private byte[] newValue = null;
    private byte[] oldValue = null;

    public ChangeClientRandomAction(byte[] newValue) {
	super();
	this.newValue = newValue;
    }

    public ChangeClientRandomAction() {
    }

    public void setNewValue(byte[] newValue) {
	this.newValue = newValue;
    }

    public byte[] getNewValue() {
	return newValue;
    }

    public byte[] getOldValue() {
	return oldValue;
    }

    @Override
    public void execute(TlsContext tlsContext, ActionExecutor executor) throws WorkflowExecutionException, IOException {
	if (executed) {
	    throw new WorkflowExecutionException("Action already executed!");
	}
	oldValue = tlsContext.getClientRandom();
	tlsContext.setClientRandom(newValue);
    }

    @Override
    public void reset() {
	oldValue = null;
    }

}
