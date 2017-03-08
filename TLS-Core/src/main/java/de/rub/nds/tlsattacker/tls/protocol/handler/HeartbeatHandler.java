/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.handler;

import de.rub.nds.tlsattacker.tls.constants.HeartbeatMode;
import de.rub.nds.tlsattacker.tls.protocol.handler.ProtocolMessageHandler;
import de.rub.nds.tlsattacker.tls.protocol.message.HeartbeatMessage;
import de.rub.nds.tlsattacker.tls.protocol.parser.HeartbeatMessageParser;
import de.rub.nds.tlsattacker.tls.protocol.parser.Parser;
import de.rub.nds.tlsattacker.tls.protocol.preparator.HeartbeatMessagePreparator;
import de.rub.nds.tlsattacker.tls.protocol.preparator.Preparator;
import de.rub.nds.tlsattacker.tls.protocol.serializer.HeartbeatMessageSerializer;
import de.rub.nds.tlsattacker.tls.protocol.serializer.Serializer;
import de.rub.nds.tlsattacker.tls.workflow.TlsContext;

/**
 * Handler for Heartbeat messages: http://tools.ietf.org/html/rfc6520#page-4
 * 
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 */
public class HeartbeatHandler extends ProtocolMessageHandler<HeartbeatMessage> {

    public HeartbeatHandler(TlsContext tlsContext) {
        super(tlsContext);
    }

    @Override
    protected Parser getParser(byte[] message, int pointer) {
        return new HeartbeatMessageParser(pointer, message);
    }

    @Override
    protected Preparator getPreparator(HeartbeatMessage message) {
        return new HeartbeatMessagePreparator(tlsContext, message);
    }

    @Override
    protected Serializer getSerializer(HeartbeatMessage message) {
        return new HeartbeatMessageSerializer(message);
    }

    @Override
    protected void adjustTLSContext(HeartbeatMessage message) {
        // TODO perhaps something to do here
    }
}