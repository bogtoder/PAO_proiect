package pack.interfaces;

import pack.entities.Colet;

public interface TransportMarfa {

    // o sa faca chestii pentru marfa

    void loadItem(Colet item);
    Double pretTransport(Colet item);
    String printColete();
}
