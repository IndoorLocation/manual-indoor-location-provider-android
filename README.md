# Manually set the Indoor Location on Android


This provider allows you to manually set the location, for example from a click on a map or for debug purposes.

## Use

Instanciate the provider:

```
manualIndoorLocationProvider = new ManualIndoorLocationProvider();
```

Set the provider in your Mapwize SDK:

```
mapwizePlugin.setLocationProvider(manualIndoorLocationProvider);   
```

Set the location

```
IndoorLocation indoorLocation = new IndoorLocation("Manual", 0, 0, 0, System.currentTimeMillis());
manualIndoorLocationProvider.setIndoorLocation(indoorLocation);
```

## Demo app

A simple demo application to test the provider is available in the /app directory.

You will need to set your credentials in ManualIndoorLocationProviderDemoApplication and MapActivity.

Sample keys are given for Mapwize and Mapbox. Please note that those keys can only be used for testing purposes, with very limited traffic, and cannot be used in production. Get your own keys from [mapwize.io](https://www.mapwize.io) and [mapbox.com](https://www.mapbox.com). Free accounts are available.

## Contribute

Contributions are welcome. We will be happy to review your PR.

## Support

For any support with this provider, please do not hesitate to contact [support@mapwize.io](mailto:support@mapwize.io)

## License

MIT