/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.workflow.action;

import de.rub.nds.tlsattacker.tls.constants.CipherSuite;
import de.rub.nds.tlsattacker.tls.exceptions.WorkflowExecutionException;
import de.rub.nds.tlsattacker.tls.record.cipher.RecordCipher;
import de.rub.nds.tlsattacker.tls.record.cipher.RecordCipherFactory;
import de.rub.nds.tlsattacker.tls.workflow.TlsContext;
import de.rub.nds.tlsattacker.tls.workflow.action.executor.ActionExecutor;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class ChangeCipherSuiteAction extends TLSAction {

    private CipherSuite newValue = null;
    private CipherSuite oldValue = null;

    public ChangeCipherSuiteAction(CipherSuite newValue) {
        // TODO can be better implemented with generics?
        super();
        this.newValue = newValue;
    }

    public ChangeCipherSuiteAction() {
    }

    public CipherSuite getNewValue() {
        return newValue;
    }

    public void setNewValue(CipherSuite newValue) {
        this.newValue = newValue;
    }

    public CipherSuite getOldValue() {
        return oldValue;
    }

    @Override
    public void execute(TlsContext tlsContext, ActionExecutor executor) throws WorkflowExecutionException {
        if (isExecuted()) {
            throw new WorkflowExecutionException("Action already executed!");
        }
        oldValue = tlsContext.getSelectedCipherSuite();
        tlsContext.setSelectedCipherSuite(newValue);
        RecordCipher recordCipher = RecordCipherFactory.getRecordCipher(tlsContext);
        tlsContext.getRecordLayer().setRecordCipher(recordCipher);
        tlsContext.getRecordLayer().updateDecryptionCipher();
        tlsContext.getRecordLayer().updateEncryptionCipher();
        LOGGER.info("Changed CipherSuite from " + oldValue.name() + " to " + newValue.name());
        setExecuted(true);
    }

    @Override
    public void reset() {
        oldValue = null;
        setExecuted(false);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.newValue);
        hash = 17 * hash + Objects.hashCode(this.oldValue);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChangeCipherSuiteAction other = (ChangeCipherSuiteAction) obj;
        if (this.newValue != other.newValue) {
            return false;
        }
        return this.oldValue == other.oldValue;
    }

}
