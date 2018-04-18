SELECT 
    afzender,
    bericht_id,
    bericht_datum,
    bericht,
    id,
    ms_sequence_number,
    verwerkt,
    mailbox_nr
FROM 
    proefSynchronisatieBericht
WHERE
    verwerkt is null OR verwerkt = false
ORDER BY
    id;