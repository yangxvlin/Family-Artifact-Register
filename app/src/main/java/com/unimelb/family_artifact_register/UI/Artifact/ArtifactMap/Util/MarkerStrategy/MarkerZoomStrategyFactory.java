package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.Util.MarkerStrategy;

/**
 * singleton factory pattern to provide a global access
 */
public class MarkerZoomStrategyFactory {
    /**
     * singleton instance
     */
    private static MarkerZoomStrategyFactory markerZoomStrategyFactory = new MarkerZoomStrategyFactory();

    /**
     * @return factory
     */
    public static MarkerZoomStrategyFactory getMarkerZoomStrategyFactory() {
        return markerZoomStrategyFactory;
    }

    /**
     * @param markerLength number of markers
     * @return strategy to zoom for markers
     */
    public MarkerZoomStrategy getMarkerZoomStrategy(int markerLength) {
        if (markerLength == 1) {
            return new SingleMarkerZoomStrategy();
        } else if (markerLength > 1) {
            return new MultipleMarkerZoomStrategy();
        } else {
            return new ZeroMarkerZoomStrategy();
        }
    }
}
