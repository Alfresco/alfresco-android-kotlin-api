package com.alfresco.core.network

import com.alfresco.core.network.contract.AlfrescoNetwork
import com.alfresco.core.network.contract.implementation.OkHttpNetwork
import com.alfresco.core.serialization.Deserializer
import com.alfresco.core.serialization.MoshiDeserializer

/**
 * A singleton object that holds the configuration
 * for the network-related implementation
 *
 * Created by Bogdan Roatis on 03 April 2019.
 */
object NetworkConfigManager {
    const val DEFAULT_TIMEOUT = 30L

    val alfrescoNetworkProtocol: AlfrescoNetwork = OkHttpNetwork()
    val deserializer: Deserializer = MoshiDeserializer()
}
